const fs = require('fs');
const path = require('path');

const packageJsonPath = path.join(__dirname, 'package.json');
const backupPackageJsonPath = path.join(__dirname, 'package.backup.json');
const projectsPath = path.join(__dirname, 'projects');

restoreBackup();

function restoreBackup() {
  // Restore backup files if any error occurs
  fs.readFile(backupPackageJsonPath, 'utf8', (err, packageData) => {
    if (!err) {
      fs.writeFile(packageJsonPath, packageData, 'utf8', (err) => {
        if (err) {
          console.error('Error restoring backup of package.json:', err);
        } else {
          console.log('Backup of package.json restored successfully');
          // پس از بازگردانی بکاپ، بکاپ رو پاک کن
          fs.unlink(backupPackageJsonPath, (err) => {
            if (err) {
              console.error('Error deleting backup of package.json:', err);
            } else {
              console.log('Backup of package.json deleted successfully');
            }
          });
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
                // پس از بازگردانی بکاپ، بکاپ رو پاک کن
                fs.unlink(backupProjectPackageJsonPath, (err) => {
                  if (err) {
                    console.error(`Error deleting backup of package.json for ${project}:`, err);
                  } else {
                    console.log(`Backup of package.json for ${project} deleted successfully`);
                  }
                });
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
