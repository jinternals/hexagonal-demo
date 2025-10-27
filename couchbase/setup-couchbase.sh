#!/bin/bash
set -m

# --- Configuration Variables ---
SETUP_CONTAINER_NAME="couchbase-setup"
# Optional: scope/collection settings
COUCHBASE_SCOPE="${COUCHBASE_SCOPE:-_default}"          # default scope
COUCHBASE_COLLECTION="${COUCHBASE_COLLECTION:-}"        # e.g. "users"
COUCHBASE_COLLECTION_MAX_TTL="${COUCHBASE_COLLECTION_MAX_TTL:-}"  # e.g. "604800"
# --- End of Configuration ---

cb_cli="/opt/couchbase/bin/couchbase-cli"
CB_HOST="couchbase"
CB_USER="$COUCHBASE_ADMINISTRATOR_USERNAME"
CB_PASS="$COUCHBASE_ADMINISTRATOR_PASSWORD"
CB_BUCKET="$COUCHBASE_BUCKET"

# Helper to run couchbase-cli
cb() {
  "$cb_cli" "$@" -c "$CB_HOST" --username "$CB_USER" --password "$CB_PASS"
}

# 1) Cluster init (idempotent)
if ! cb server-list &>/dev/null; then
  echo "Initializing the Couchbase cluster..."
  $cb_cli cluster-init \
    -c "$CB_HOST" \
    --cluster-username "$CB_USER" \
    --cluster-password "$CB_PASS" \
    --services data,index,query \
    --cluster-ramsize 512 \
    --cluster-index-ramsize 512 || {
      echo "Failed to initialize cluster."
      exit 1
    }
  echo "Cluster initialized successfully."
else
  echo "Couchbase cluster is already initialized."
fi

# 2) Bucket ensure (idempotent)
if ! cb bucket-list | grep -q "^$CB_BUCKET$"; then
  echo "Creating bucket '$CB_BUCKET'..."
  cb bucket-create \
    --bucket "$CB_BUCKET" \
    --bucket-type couchbase \
    --bucket-ramsize 256 \
    --enable-flush 1 || {
      echo "Failed to create bucket '$CB_BUCKET'."
      exit 1
    }
  echo "Bucket '$CB_BUCKET' created successfully."
else
  echo "Bucket '$CB_BUCKET' already exists."
fi

# 3) User ensure (idempotent)
if ! cb user-manage --list | grep -q "$COUCHBASE_BUCKET_USER"; then
  echo "Creating or updating user '$COUCHBASE_BUCKET_USER'..."
  cb user-manage \
    --set \
    --rbac-username "$COUCHBASE_BUCKET_USER" \
    --rbac-password "$COUCHBASE_BUCKET_PASSWORD" \
    --rbac-name "My Application User" \
    --roles "bucket_full_access[$CB_BUCKET]" \
    --auth-domain local || {
      echo "Failed to create or update user '$COUCHBASE_BUCKET_USER'."
      exit 1
    }
  echo "User '$COUCHBASE_BUCKET_USER' created or updated successfully."
else
  echo "User '$COUCHBASE_BUCKET_USER' already exists."
fi

# 4) Scope ensure (idempotent)
# Skip creation if _default (always exists).
if [ -n "$COUCHBASE_SCOPE" ] && [ "$COUCHBASE_SCOPE" != "_default" ]; then
  if ! cb collection-manage --bucket "$CB_BUCKET" --list-scopes | grep -wq "$COUCHBASE_SCOPE"; then
    echo "Creating scope '$COUCHBASE_SCOPE' in bucket '$CB_BUCKET'..."
    if ! cb collection-manage --bucket "$CB_BUCKET" --create-scope "$COUCHBASE_SCOPE"; then
      echo "Failed to create scope '$COUCHBASE_SCOPE'."
      exit 1
    fi
    echo "Scope '$COUCHBASE_SCOPE' created."
  else
    echo "Scope '$COUCHBASE_SCOPE' already exists."
  fi
fi

# 5) Collection ensure (idempotent)
if [ -n "$COUCHBASE_COLLECTION" ]; then
  if ! cb collection-manage --bucket "$CB_BUCKET" --list-collections "$COUCHBASE_SCOPE" | grep -wq "$COUCHBASE_COLLECTION"; then
    echo "Creating collection '$COUCHBASE_COLLECTION' in scope '$COUCHBASE_SCOPE'..."
    # Build creation command with optional max TTL
    CREATE_ARGS=(collection-manage --bucket "$CB_BUCKET" --create-collection "${COUCHBASE_SCOPE}.${COUCHBASE_COLLECTION}")
    if [ -n "$COUCHBASE_COLLECTION_MAX_TTL" ]; then
      CREATE_ARGS+=(--max-ttl "$COUCHBASE_COLLECTION_MAX_TTL")
    fi
    if ! cb "${CREATE_ARGS[@]}"; then
      echo "Failed to create collection '${COUCHBASE_SCOPE}.${COUCHBASE_COLLECTION}'."
      exit 1
    fi
    echo "Collection '${COUCHBASE_SCOPE}.${COUCHBASE_COLLECTION}' created."
  else
    echo "Collection '$COUCHBASE_COLLECTION' already exists in scope '$COUCHBASE_SCOPE'."
  fi
fi

echo "Couchbase setup script finished."
