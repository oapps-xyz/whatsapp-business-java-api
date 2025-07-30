#!/bin/bash

# Script to manually publish the package to GitHub Packages
# Usage: ./scripts/publish.sh

echo "🚀 Publishing WhatsApp Business Java API to GitHub Packages..."

# Check if GITHUB_TOKEN is set
if [ -z "$GITHUB_TOKEN" ]; then
    echo "❌ Error: GITHUB_TOKEN environment variable is not set"
    echo "Please set your GitHub token:"
    echo "export GITHUB_TOKEN=your_github_token_here"
    exit 1
fi

# Create Maven settings with GitHub token
mkdir -p ~/.m2
cat > ~/.m2/settings.xml << EOF
<settings>
    <servers>
        <server>
            <id>github</id>
            <username>$(git config user.name)</username>
            <password>$GITHUB_TOKEN</password>
        </server>
    </servers>
</settings>
EOF

echo "📦 Building and publishing package..."
mvn clean deploy

if [ $? -eq 0 ]; then
    echo "✅ Package published successfully!"
    echo "📋 You can now use the latest version in your projects"
else
    echo "❌ Failed to publish package"
    exit 1
fi 