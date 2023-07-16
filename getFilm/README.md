#Video-streaming-function
Accepts a query param: videoName and fetches video from cloud storage

##Windows
gcloud alpha functions deploy getFilmFunction  --entry-point com.example.videostreamfunction.GetFilm --runtime java17 --memory 512MB --region europe-central2 --trigger-http --allow-unauthenticated
