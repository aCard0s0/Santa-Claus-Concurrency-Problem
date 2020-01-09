xterm  -T "Repository Server" -hold -e "./RepositoryDeployAndRun.sh" &

sleep 10

xterm  -T "SantaHouse Server" -hold -e "./SantaHouseDeployAndRun.sh" &
xterm  -T "ToyFactory Server" -hold -e "./ToyFactoryDeployAndRun.sh" &
xterm  -T "SouthPacific Server" -hold -e "./SouthPacificDeployAndRun.sh" &
xterm  -T "TripAroundWorld Server" -hold -e "./TripAroundWorldDeployAndRun.sh" &
xterm  -T "Stable Server" -hold -e "./StableDeployAndRun.sh" &

sleep 5

xterm  -T "Reindeer Client" -hold -e "./ReindeerDeployAndRun.sh" &
xterm  -T "Gnome Client" -hold -e "./GnomeDeployAndRun.sh" &

sleep 12

xterm  -T "Santa Client" -hold -e "./SantaDeployAndRun.sh" &