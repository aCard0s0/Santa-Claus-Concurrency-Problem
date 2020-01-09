echo "Apagar info das maquinas"
sshpass -f password ssh cd0308@l040101-ws01.ua.pt 'rm -rf teste'
sshpass -f password ssh cd0308@l040101-ws02.ua.pt 'rm -rf teste'
sshpass -f password ssh cd0308@l040101-ws03.ua.pt 'rm -rf teste'
sshpass -f password ssh cd0308@l040101-ws04.ua.pt 'rm -rf teste'
sshpass -f password ssh cd0308@l040101-ws05.ua.pt 'rm -rf teste'
sshpass -f password ssh cd0308@l040101-ws07.ua.pt 'rm -rf teste'
sshpass -f password ssh cd0308@l040101-ws08.ua.pt 'rm -rf teste'
sshpass -f password ssh cd0308@l040101-ws09.ua.pt 'rm -rf teste'
sshpass -f password ssh cd0308@l040101-ws11.ua.pt 'rm -rf teste'
echo "done"