package com.fanhq.example.proxy;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Hachel on 2018/10/30
 */
public class JettyTest {

    public static void main(String[] args) {
        try {
            Server server = new Server();
            ServerConnector connector = new ServerConnector(server);
            connector.setPort(8090);
            server.setConnectors(new Connector[]{connector});
            HandlerCollection handlerc = new HandlerCollection();
            handlerc.setHandlers(new Handler[]{new JettyServerHandler()});
            server.setHandler(handlerc);

            server.start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static class JettyServerHandler extends AbstractHandler {

        @Override
        public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException, ServletException {
            // invoke
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            request.setHandled(true);

            OutputStream out = response.getOutputStream();
            out.write("hello world".getBytes());
            out.flush();
        }
    }
}
