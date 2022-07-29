package ua.issoft.yurii.kupchyn;

import ua.issoft.yurii.kupchyn.requests.Request;
import ua.issoft.yurii.kupchyn.responses.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class ServerClientProtocol implements AutoCloseable {

    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    public ServerClientProtocol(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
    }


    public void sendRequest(Request request) throws IOException {
        out.writeObject(request);
        out.flush();
    }


    public void sendResponse(Response response) throws IOException {
        out.writeObject(response);
        out.flush();
    }

    public Response receiveResponse() throws IOException, ClassNotFoundException {
        return (Response) in.readObject();
    }

    public Request receiveRequest() throws IOException, ClassNotFoundException {
        return (Request) in.readObject();
    }


    @Override
    public void close() throws Exception {
        close(socket, out, in);
    }

    private void close(AutoCloseable... autoCloseables)  {
        final Exception[] ex = {null};
        boolean closeFailed = Arrays.stream(autoCloseables)
                .filter(Objects::nonNull)
                .reduce(false, (isFailed, autoCloseable) -> {
                    try {
                        autoCloseable.close();
                    } catch (Exception e) {
                        ex[0] = e;
                        return true;
                    }
                    return isFailed;
                }, (isFailed1, isFailed2) -> isFailed1 || isFailed2);

        if (closeFailed) {
            throw new ServerClientProtocolException("Closing of Resources failed", ex[0]);
        }
    }
}
