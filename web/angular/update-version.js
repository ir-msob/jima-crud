const fs = require('fs');
const path = require('path');

const packageJsonPath = path.join(__dirname, 'package.json');
const backupPackageJsonPath = path.join(__dirname, 'package.backup.json');
const configPath = path.join(__dirname, '../../project.properties');
const projectsPath = path.join(__dirname, 'projects');

fs.readFile(configPath, 'utf8', (err, data) => {
  if (err) {
    console.error('Error reading config file:', err);
    return restoreBackup();
  }

  // Parse project.properties data to find keys and values
  const keyValuePairs = data.trim().split('\n').map(line => {
    const [key, value] = line.split('=');
    return [key.trim(), value.trim()];
  });

  // Prepare replacement map
  const replacements = new Map(keyValuePairs);

  // Read and update package.json
  fs.readFile(packageJsonPath, 'utf8', (err, packageData) => {
    if (err) {
      console.error('Error reading package.json:', err);
      return restoreBackup();
    }

    // Backup original package.json
    fs.writeFile(backupPackageJsonPath, packageData, 'utf8', (err) => {
      if (err) {
        console.error('Error creating backup of package.json:', err);
        return restoreBackup();
      }
      console.log('Backup of package.json created successfully');
    });

    // Replace placeholders with actual values
    let updatedPackageJson = packageData;
    replacements.forEach((value, key) => {
      console.error('key:', key);
      updatedPackageJson = updatedPackageJson.replace(new RegExp(`\\$${key}`, 'g'), value);
    });

    // Write the updated package.json file
    fs.writeFile(packageJsonPath, updatedPackageJson, 'utf8', (err) => {
      if (err) {
        console.error('Error writing package.json:', err);
        return restoreBackup();
      }

      console.log('package.json updated successfully');
    });
  });

  // Read and update package.json files inside projects directory
  fs.readdir(projectsPath, (err, projects) => {
    if (err) {
      console.error('Error reading projects directory:', err);
      return restoreBackup();
    }

    projects.forEach(project => {
      const projectDir = path.join(projectsPath, project);
      const projectPackageJsonPath = path.join(projectDir, 'package.json');
      const backupProjectPackageJsonPath = path.join(projectDir, 'package.backup.json');

      fs.readFile(projectPackageJsonPath, 'utf8', (err, data) => {
        if (err) {
          console.error(`Error reading package.json for ${project}:`, err);
          return;
        }

        // Backup original package.json
        fs.writeFile(backupProjectPackageJsonPath, data, 'utf8', (err) => {
          if (err) {
            console.error('Error creating backup of package.json:', err);
            return restoreBackup();
          }
          console.log('Backup of package.json created successfully');
        });

        try {
          // Replace placeholders with actual values
          let updatedProjectPackageJson = data;
          replacements.forEach((value, key) => {
            console.log('key value',key,value);
            updatedProjectPackageJson = updatedProjectPackageJson.replace(new RegExp(`\\$${key}`, 'g'), value);
          });

          // Write updated package.json for each project
          fs.writeFile(projectPackageJsonPath, updatedProjectPackageJson, 'utf8', (err) => {
            if (err) {
              console.error(`Error writing package.json for ${project}:`, err);
              return restoreBackup();
            }
            console.log(`package.json for ${project} updated successfully`);
          });
        } catch (parseError) {
          console.error(`Error parsing package.json for ${project}:`, parseError);
        }
      });
    });
  });
});

function restoreBackup() {
  // Restore backup files if any error occurs
  fs.readFile(backupPackageJsonPath, 'utf8', (err, packageData) => {
    if (!err) {
      fs.writeFile(packageJsonPath, packageData, 'utf8', (err) => {
        if (err) {
          console.error('Error restoring backup of package.json:', err);
        } else {
          console.log('Backup of package.json restored successfully');
        }
      });
    } else {
      console.error('No backup found to restore package.json');
    }
  });

  // Restore backups of project package.json files
  fs.readdir(projectsPath, (err, projects) => {
    if (!err) {
      projects.forEach(project => {
        const projectDir = path.join(projectsPath, project);
        const backupProjectPackageJsonPath = path.join(projectDir, 'package.backup.json');
        const projectPackageJsonPath = path.join(projectDir, 'package.json');

        fs.readFile(backupProjectPackageJsonPath, 'utf8', (err, data) => {
          if (!err) {
            fs.writeFile(projectPackageJsonPath, data, 'utf8', (err) => {
              if (err) {
                console.error(`Error restoring backup of package.json for ${project}:`, err);
              } else {
                console.log(`Backup of package.json for ${project} restored successfully`);
              }
            });
          } else {
            console.error(`No backup found to restore package.json for ${project}`);
          }
        });
      });
    } else {
      console.error('Error reading projects directory for restoring backups:', err);
    }
  });
}
