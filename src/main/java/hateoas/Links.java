package hateoas;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.UriBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import serializers.LinksSerializer;

/**
 * Created by timp on 13/11/2015.
 */
@JsonSerialize(using = LinksSerializer.class)
public class Links {

    private final List<Link> links;

    public Links(){
        links = new ArrayList<>();
    }

    public void addLink(String rel, Class resourceClass, String path){
        links.add(new Link(
                rel,
                UriBuilder.fromResource(resourceClass).path(path).build()));
    }

    public void addLink(String rel, Class resourceClass){
        links.add(new Link(
                rel,
                UriBuilder.fromResource(resourceClass).build()));
    }

    public List<Link> getLinks() {
        return links;
    }

    private class Link {
        @JsonProperty
        private final String rel;

        @JsonProperty
        private final URI href;

        private Link(String rel, URI href){
            this.rel = rel;
            this.href = href;
        }

        public String getRel() {
            return rel;
        }

        public URI getHref() {
            return href;
        }
    }

}
