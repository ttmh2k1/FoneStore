package hcmute.fonestore.object;

public class category2 {
    private String Title;
    private int Thumbnail;

    public category2(){
    }

    public category2(String title, int thumbnail){
        Title = title;
        Thumbnail = thumbnail;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        Thumbnail = thumbnail;
    }
}