/*
 * SPDX-License-Identifier: Apache-2.0
 */

package org;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import com.owlike.genson.Genson;

@DataType()
public class Project {

    private final static Genson genson = new Genson();

    // Project id.
    @Property()
    private String id;
    
    public String getId() {
        return id;
    }

    public void setId(String Id){
        this.id = Id;
    }

    // Project name.
    @Property()
    private String name;

    public String getName(){
        return this.name;
    }

    public void setName(String voterName){
        this.name = voterName;
    }

    // Project received votes.
    @Property()
    private Integer totalVotes;
    
    public Integer getVotes() {
        return this.totalVotes;
    }

    public void setVotes(Integer votes) {
        this.totalVotes = votes;
    }

    public void addVotes(Integer votes){
        this.totalVotes += votes;
    }

    // Constructors.
    public Project(){
        // Empty constructor.
    }

    public Project(String id, String name, Integer votes){
        this.id = id;
        this.name = name;
        this.totalVotes = votes;
    }

    // Json parsing.
    public String toJSONString() {
        return genson.serialize(this);
    }

    public static Project fromJSONString(String json) {
        Project asset = genson.deserialize(json, Project.class);
        return asset;
    }
}
