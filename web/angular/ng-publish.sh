#!/bin/bash

# Read version from external file
version=$(grep 'jima-commons.version' ../../project.properties | cut -d '=' -f2)
echo "Read jima-commons.version $version"

# Install root dependencies
echo "Installing root dependencies..."
npm install

# Function to install dependencies, build, and publish a project
build_and_publish_project() {
  local project_name=$1
  local dependencies=${2:-""}

  echo "Building and publishing $project_name..."

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

  # Publish the package
  echo "Publishing $project_name..."
  npm publish --access public

  cd ../..
}

# Build and publish Core Commons
build_and_publish_project "core-commons"

# Build and publish Core Restful
build_and_publish_project "core-restful" "@ir-msob/jima-core-commons@$version"

echo "All projects built and published successfully."
