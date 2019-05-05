(ns clj-fest-playlist.core
  (:require [clj-spotify.core :as sptfy]
            [clojure.edn :as edn]
            [clojure.pprint :as p])
  (:gen-class))

(defn get-artist-info-by-name
  "get info for an artist"
  [token artist-name]
  (let [query-results (sptfy/search {:q artist-name :type "artist"} token)
        artist-info (first (get-in query-results [:artists :items]))]

      {:search-name artist-name :name (get artist-info :name) :id (get artist-info :id)}))



(defn get-top-tracks-by-artist-id
  "get top tracks for an artist"
  [token artist-id num-tracks]
  (let [query-results (sptfy/get-an-artists-top-tracks {:id artist-id :country "US"} token)
        full-track-infos (take num-tracks (get query-results :tracks))]

      (for [track full-track-infos] {:name (get track :name)
                                     :artist (get (first (get track :artists)) :name)
                                     :uri (get track :uri)})))



(defn -main
  "Build a playlist from a list of bands"
  [& args]
  (let [options (butlast args)
        config-file (last args)
        config (->> (slurp config-file)
                    (edn/read-string))
        token (System/getenv "SPOTIFY_TOKEN");(get config :token)
        playlist-name (get config :playlist-name)
        user-id (System/getenv "SPOTIFY_USER");(get config :user-id)
        artist-names (get config :artist-names)
        songs-per-artist (get config :songs-per-artist)
        artist-infos (map #(get-artist-info-by-name token %) artist-names)
        missing-artists (filter #(nil? (get % :id)) artist-infos)
        found-artists (filter #(not (nil? (get % :id))) artist-infos)
        artist-ids (concat (map #(get % :id) found-artists) (get config :finicky-artist-ids))]

    (println "No matches found for:")
    (p/pprint missing-artists)

    (println "Artists found (Check to make sure they are what we expect):")
    (p/pprint (map #(str (get % :search-name) " -> " (get % :name)) found-artists))

    (if (= "yes" (do (print "Create playlist (anything other than 'yes' will exit)? ") (flush) (read-line)))
     (do
      (println (str "Creating a playlist called [" playlist-name "]"))
      (let
        [tracks (flatten (map #(get-top-tracks-by-artist-id token % songs-per-artist) artist-ids))
         track-uri-partitions (partition 100 100 nil (map #(get % :uri) tracks))
         playlist (sptfy/create-a-playlist {:user_id user-id :name playlist-name :public true} token)
         playlist-id (get playlist :id)]


        (println (str "adding " (count tracks ) " tracks:"))
        ; (p/pprint track-uri-partitions)

        (print "playlist:")
        (p/pprint playlist)

        (doseq [track-uris track-uri-partitions]
               (println (str "adding " (count track-uris) " tracks"))
               (sptfy/add-tracks-to-a-playlist {:user_id user-id :playlist_id playlist-id :uris track-uris} token)))))))
