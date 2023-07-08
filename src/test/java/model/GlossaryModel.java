package model;

import com.google.gson.annotations.SerializedName;

public class GlossaryModel {
    private String title;

    @SerializedName("gloss_div")
    private GlossDivModel glossDiv;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public GlossDivModel getGlossDiv() {
        return glossDiv;
    }

    public void setGlossDiv(GlossDivModel glossDiv) {
        this.glossDiv = glossDiv;
    }

    public static class GlossDivModel {
        private String title;
        private boolean flag;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }
    }
}
