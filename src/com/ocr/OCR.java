
package com.ocr;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.jdesktop.swingx.util.OS;

import com.test.Config;

public class OCR {
    private final String LANG_OPTION = "-l"; // 英文字母小写l，并非数字1
   // private final String OPTION = "-psm"; // 英文字母小写l，并非数字1
    private final String EOL = System.getProperty("line.separator");
    private String tessPath = Config.ocrInstallPath;
    private String imgPath = "test.jpg";

    public String recognizeText(File imageFile, String imageFormat) throws Exception {
        imageFile = ImageIOHelper.createImage(imageFile,imageFormat);
        
        /*** 调用专业接口**begin **/
        BufferedImage image_ex;
        image_ex = ImageIO.read(imageFile);
        ImageFilter filter = new ImageFilter(image_ex);
        ;
        File file_ex = new File(imageFile.getParentFile(), imgPath);
        ImageIO.write(filter.lineGrey(), "JPEG", file_ex);// lineGrey效果已经算不错的了，但是还是识别不出来
        /*** 调用专业接口**end **/

        File outputFile = new File(imageFile.getParentFile(), "output");
        StringBuffer strB = new StringBuffer();
        List<String> cmd = new ArrayList<String>();
        if (OS.isLinux()) {
            cmd.add("tesseract");
        } else {
            cmd.add(tessPath + "\\tesseract");
        }
        cmd.add("");
        cmd.add(outputFile.getName());
        cmd.add(LANG_OPTION);
        cmd.add("eng");
        //cmd.add(OPTION);
        //cmd.add("7");

        ProcessBuilder pb = new ProcessBuilder();
        pb.directory(imageFile.getParentFile());

        cmd.set(1, imgPath);
        pb.command(cmd);
        pb.redirectErrorStream(true);

        Process process = pb.start();
        int w = process.waitFor();

        if (w == 0) {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(
                    outputFile.getAbsolutePath() + ".txt"), "UTF-8"));
            String str;
            while ((str = in.readLine()) != null) {
                strB.append(str).append(EOL);
            }
            in.close();
        } else {
            String msg;
            switch (w) {
                case 1:
                    msg = "Errors accessing files.There may be spaces in your image's filename.";
                    break;
                case 29:
                    msg = "Cannot recongnize the image or its selected region.";
                    break;
                case 31:
                    msg = "Unsupported image format.";
                    break;
                default:
                    msg = "Errors occurred.";
            }
            throw new RuntimeException(msg);
        }
        new File(outputFile.getAbsolutePath() + ".txt").delete();
        return strB.toString();
    }
}
