echo ""
echo "	SCENES ON THE LIFE OF SANTA CLAUS - Problem developer with Socket Comunication"
echo "		by André Cardoso 65069 & Joao Peixe 64649"
echo "Compressing data to be sent to the TripAroundWorldServer side node."
rm -rf TripAroundWorldServer.zip
zip -rq TripAroundWorldServer.zip client configs identities interfaces main proxys servers shareRegions states structures
echo "Transfering data to the TripAroundWorldServer side node."
sshpass -f password ssh cd0308@l040101-ws09.ua.pt 'mkdir -p teste/SantaProblem'
sshpass -f password ssh cd0308@l040101-ws09.ua.pt 'rm -rf teste/SantaProblem/*'
sshpass -f password scp TripAroundWorldServer.zip cd0308@l040101-ws09.ua.pt:teste/SantaProblem
echo "Decompressing data sent to the TripAroundWorldServer side node."
sshpass -f password ssh cd0308@l040101-ws09.ua.pt 'cd teste/SantaProblem ; unzip -q TripAroundWorldServer.zip'
echo "Compiling program files at the TripAroundWorldServer side node."
sshpass -f password ssh cd0308@l040101-ws09.ua.pt 'cd teste/SantaProblem ; javac */*.java'
sleep 1
echo "Executing program at the server side node."
sshpass -f password ssh cd0308@l040101-ws09.ua.pt 'cd teste/SantaProblem ; java servers.ServerTrip'
