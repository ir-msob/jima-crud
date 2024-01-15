for project in $(ls projects/); do
  ng build --project=$project
done
