#Video-streaming-function
Accepts multipart video file (only mp4) with query param "description" and posts video file to cloud storage and adds descritpion to blob's metadata

##Windows
gcloud alpha functions deploy postFilmFunction  --entry-point com.example.videostreamfunction.PostFilm --runtime java17 --memory 512MB --region europe-central2 --trigger-http --allow-unauthenticated