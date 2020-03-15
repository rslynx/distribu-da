package labnfs;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Servidor2 {
    public static void main(String[] args) throws IOException {
        System.out.println("== Servidor ==");
        // Configurando o socket
        ServerSocket serverSocket = new ServerSocket(7001);
        Socket socket = serverSocket.accept();

        // pegando uma referência do canal de saída do socket. Ao escrever nesse canal, está se enviando dados para o
        // servidor
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        // pegando uma referência do canal de entrada do socket. Ao ler deste canal, está se recebendo os dados
        // enviados pelo servidor
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        String currentDir = "C:\\Users\\Carlo\\Desktop\\distribu-da-master\\labnfs";
        Path origem = Paths.get(currentDir);
        // laço infinito do servidor
        while (true) {
            System.out.println("Cliente: " + socket.getInetAddress());
            String[] command = dis.readUTF().split(" ");
            System.out.println(command[0]);
            switch (command[0]) {
                case "readdir":
                    currentDir = command[1];
                    File folder = new File(currentDir);
                    File[] listOfFiles = folder.listFiles();
                    String out = "\n";
                    for (File file : listOfFiles) {
                        if (file.isFile()) {
                            out += file.getName() + "\n";
                            System.out.println(file.getName());
                        }
                    }
                    dos.writeUTF("Listagem de arquivos\n" + out);
                    break;
                case "create":
                    currentDir = command[1];
                    File newfile = new File(currentDir);
                    newfile.createNewFile();
                    dos.writeUTF("Arquivo criado com sucesso");
                    break;
                case "delete":
                    currentDir = command[1];
                    File delfile = new File(currentDir);
                    delfile.delete();
                    dos.writeUTF("Arquivo deletado com sucesso");
                    break;
                case "rename":
                    String oldName = command[1];
                    String newName = command[2];
                    File f1 = new File(oldName);
                    File f2 = new File(newName);
                    boolean b = f1.renameTo(f2);
                    dos.writeUTF("Arquivo renomeado com sucesso");
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + command[0]);
            }



        }

    }


}
