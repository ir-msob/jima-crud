for project in $(ls projects/); do
  ng test --project=$project --watch=false --browsers=ChromeHeadless
done
