package com.mec.csfreamwork.core;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public abstract class Communication implements Runnable{
	private Socket socket;
	private DataInputStream dis;
	private DataOutputStream dos;
	private volatile boolean goon;
	
	public Communication(Socket socket) {
		try {
			this.socket = socket;
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
			goon = true;
		} catch (IOException e) {
			close();
		}
	}	

	@Override
	public void run() {
		while (goon) {
			try {
				String message = dis.readUTF();
				dealMessage(new NetMessage(message));
			} catch (IOException e) {
				if (goon == true) {
					dealPeerDrop();
				}
				close();
			}
		}
		close();
	}

	public void sendMessage(NetMessage message) {
		try {
			if (dos == null) {
				return;
			}
			dos.writeUTF(message.toString());
		} catch (IOException e) {
			close();
		}
	}

	public void close() {
		goon = false;
		if (dis != null) {
			try {
				dis.close();
			} catch (IOException e) {
			} finally {
				dis = null;
			}
		}
		if (dos != null) {
			try {
				dos.close();
			} catch (IOException e) {
			} finally {
				dos = null;
			}
		}
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
			} finally {
				socket = null;
			}
		}
	}
	
	protected abstract void dealPeerDrop();
	protected abstract void dealMessage(NetMessage message);
	
	
}
