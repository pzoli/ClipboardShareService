package hu.infokristaly.backupservice;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@RestController
@RequestMapping("/clipboard")
public class ClipboardController {

	@PostMapping(value = "/post")
	public void putContentToClipboard(@RequestParam("content") String content) {
		StringSelection selection = new StringSelection(content);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(selection, null);
	}

	@PostMapping(value = "/jsonpost")
	public String putContentFromJSonToClipboard(@RequestBody ClipboardContent clipboardContent) {
		StringSelection selection = new StringSelection(clipboardContent.content);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(selection, null);
		return "ready";
	}

	@GetMapping(value = "/get")
	public String getContentFromClipboard() {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		String result = "";
		try {
			result = (String) clipboard.getData(DataFlavor.stringFlavor);
		} catch (UnsupportedFlavorException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	@Autowired
    Environment environment;
	
	@GetMapping(value = "/qrcode/{type}", produces = MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody byte[] generateQRCodeGet(@PathVariable String type) {
		String hostname = environment.getProperty("server.address");
		String port = environment.getProperty("local.server.port");

		QRCodeWriter barcodeWriter = new QRCodeWriter();
	    BitMatrix bitMatrix = null;
		try {
			bitMatrix = barcodeWriter.encode("http://"+hostname+":"+port+"/clipboard/"+type, BarcodeFormat.QR_CODE, 200, 200);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		BufferedImage img = MatrixToImageWriter.toBufferedImage(bitMatrix);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(img, "jpg", baos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return baos.toByteArray();
	}
}
