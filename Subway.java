package sub;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import sub.sub.model.Result;
import sub.sub.model.Station;
import sub.sub.util.DataBuilder;
import sub.sub.util.SubwayUtil;

public class Subway {

    public static void main(String[] args) throws IOException {
        if(args[0].equals("-map"))
            new DataBuilder(args[1]);
        else if(args[0].equals("-a")) {
            if(args[2].equals("-map"))
                new DataBuilder(args[3]);
            else return;
            List<Station> stations=SubwayUtil.getLineStation(args[1]);
            String content="";
            if(stations==null)
                content="不存在该地铁线路";
            else {
                for(int i=0;i<stations.size();i++) {
                    if(i==stations.size()-1&&stations.get(i).getLinkStations().contains(stations.get(0)))
                        content=content+stations.get(i).getName()+" --该线路为环线--";
                    else
                        content=content+stations.get(i).getName()+" ";
                }
            }
            if(args[4].equals("-o")) {
                printToTxt(content,args[5]);
            }
            else return;
        }
        else if(args[0].equals("-b")) {
            if(args[3].equals("-map"))
                new DataBuilder(args[4]);
            else return;
            Station star=null;
            Station end=null;
            for(List<Station> l:DataBuilder.lineSet){
                for(int k=0;k<l.size();k++) {
                    if(l.get(k).getName().equals(args[1])) {
                        star=l.get(k);
                    }
                    if(l.get(k).getName().equals(args[2])) {
                        end=l.get(k);
                    }
                }
            }
            String context="";
            if(star==null||end==null)
                context="输入的站点名不存在";
            else {
                Result r=SubwayUtil.shortestPath(star,end);
                List<String> path=SubwayUtil.getPath(r);
                context=(r.getDistance())+"\n";
                for(String s:path)
                    context=context+s+"\n";
            }
            if(args[5].equals("-o")) {
                printToTxt(context,args[6]);
            }
            else
                return;
        }
    }
    public static void printToTxt(String content,String path) throws IOException {
        String str = "C:\\Users\\HP\\Desktop\\"+path;

        File file = new File(str);
        if(!file.exists()){
            file.createNewFile();
        }
        FileOutputStream outputStream = new FileOutputStream(file);
        byte[]  bytes = content.getBytes("UTF-8");//因为中文可能会乱码，这里使用了转码，转成UTF-8；
        outputStream.write(bytes);
        outputStream.close();
    }

}
