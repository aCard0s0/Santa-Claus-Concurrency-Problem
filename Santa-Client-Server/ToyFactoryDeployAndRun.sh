echo ""
echo "	SCENES ON THE LIFE OF SANTA CLAUS - Problem developer with Socket Comunication"
echo "		by André Cardoso 65069 & Joao Peixe 64649"
echo "Compressing data to be sent to the ToyFactoryServer side node."
rm -rf ToyFactoryServer.zip
zip -rq ToyFactoryServer.zip client configs identities interfaces main proxys servers shareRegions states structures
echo "Transfering data to the ToyFactoryServer side node."
sshpass -f password ssh cd0308@l040101-ws07.ua.pt 'mkdir -p teste/SantaProblem'
sshpass -f password ssh cd0308@l040101-ws07.ua.pt 'rm -rf teste/SantaProblem/*'
sshpass -f password scp ToyFactoryServer.zip cd0308@l040101-ws07.ua.pt:teste/SantaProblem
echo "Decompressing data sent to the ToyFactoryServer side node."
sshpass -f password ssh cd0308@l040101-ws07.ua.pt 'cd teste/SantaProblem ; unzip -q ToyFactoryServer.zip'
echo "Compiling program files at the ToyFactoryServer side node."
sshpass -f password ssh cd0308@l040101-ws07.ua.pt 'cd teste/SantaProblem ; javac */*.java'
sleep 1
echo "Executing program at the server side node."
sshpass -f password ssh cd0308@l040101-ws07.ua.pt 'cd teste/SantaProblem ; java servers.ServerFactory'