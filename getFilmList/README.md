# Video-stream-function
Returns a json with a list of Video entities 

## Windows
gcloud alpha functions deploy getFilmListFunction  --entry-point org.springframework.cloud.function.adapter.gcp.GcfJarLauncher --runtime java17 --source target/deploy --memory 512MB --region europe-central2 --trigger-http --allow-unauthenticated --vpc-connector video-stream-functions