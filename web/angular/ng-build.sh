#!/bin/bash

# Install root dependencies
echo "Installing root dependencies..."
npm install

# Function to install dependencies and build a project
build_project() {
  local project_name=$1
  local dependencies=${2:-""}

  echo "Building $project_name..."

  cd "projects/$project_name" || exit
  if [ -n "$dependencies" ]; then
    npm link $dependencies
  fi
  npm install

  # Run tests
  echo "Running tests for $project_name..."
  ng test --project="$project_name" --watch=false --browsers=ChromeHeadless

  # Build the project
  echo "Building $project_name..."
  ng build $project_name

  # Navigate to dist directory and create npm link
  cd "../../dist/$project_name" || exit
  npm link
  cd ../..
}

# Build CRUD Restful
build_project "crud-restful"

echo "All projects built successfully."