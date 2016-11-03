package com.flickr.photoapp.photos;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "page",
        "pages",
        "perpage",
        "total",
        "photo"
})
public class Photos {

    @JsonProperty("page")
    private Integer page;
    @JsonProperty("pages")
    private Integer pages;
    @JsonProperty("perpage")
    private Integer perpage;
    @JsonProperty("total")
    private String total;
    @JsonProperty("photo")
    private List<Photo> photo = new ArrayList<Photo>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The page
     */
    @JsonProperty("page")
    public Integer getPage() {
        return page;
    }

    /**
     * @param page The page
     */
    @JsonProperty("page")
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * @return The pages
     */
    @JsonProperty("pages")
    public Integer getPages() {
        return pages;
    }

    /**
     * @param pages The pages
     */
    @JsonProperty("pages")
    public void setPages(Integer pages) {
        this.pages = pages;
    }

    /**
     * @return The perpage
     */
    @JsonProperty("perpage")
    public Integer getPerpage() {
        return perpage;
    }

    /**
     * @param perpage The perpage
     */
    @JsonProperty("perpage")
    public void setPerpage(Integer perpage) {
        this.perpage = perpage;
    }

    /**
     * @return The total
     */
    @JsonProperty("total")
    public String getTotal() {
        return total;
    }

    /**
     * @param total The total
     */
    @JsonProperty("total")
    public void setTotal(String total) {
        this.total = total;
    }

    /**
     * @return The photo
     */
    @JsonProperty("photo")
    public List<Photo> getPhoto() {
        return photo;
    }

    /**
     * @param photo The photo
     */
    @JsonProperty("photo")
    public void setPhoto(List<Photo> photo) {
        this.photo = photo;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}