# Video-stream-function
Event-driven function, triggered by cloud storage event: metadataUpdate, fetches 
video name and description and persists it in database as Video entity
## Windows
gcloud alpha functions deploy persistFilmInfoFunction --entry-point org.springframework.cloud.function.adapter.gcp.GcfJarLauncher --trigger-event google.storage.object.metadataUpdate --trigger-resource video-streaming-functions-bucket --runtime java17 --source target/deploy --memory 512MB --region europe-central2  --service-account 257900061595-compute@developer.gserviceaccount.com --vpc-connector video-stream-functions --allow-unauthenticated