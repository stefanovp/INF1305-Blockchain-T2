/*
 * SPDX-License-Identifier: Apache-2.0
 */

package org;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import com.owlike.genson.Genson;

@DataType()
public class Voter {

    private final static Genson genson = new Genson();

    // Voter id.
    @Property()
    private String id;
    
    public String getId() {
        return id;
    }

    public void setId(String Id){
        this.id = Id;
    }

    // Voter Name.
    @Property()
    private String name;

    public String getName(){
        return this.name;
    }

    public void setName(String voterName){
        this.name = voterName;
    }

    // Vote Weight.
    @Property()
    private Integer weight;
    
    public Integer getWeight() {
        return this.weight;
    }

    public void setWeight(Integer weightVal) {
        this.weight = weightVal;
    }

    // Voter vote status.
    // Voted == true means Voter has already voted.
    // hasVoted == false means voter hasnt voted yet.
    @Property()
    private boolean hasVoted;
    
    public boolean gethasVoted() {
        return hasVoted;
    }

    public void sethasVoted(boolean newStatus) {
        this.hasVoted = newStatus;
    }

    // Constructors.
    public Voter(){
        this.hasVoted = false;
    }

    public Voter(String id, String name, Integer weight){
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.hasVoted = false;
    }

    public Voter(String id, String name, Integer weight, boolean hasVoted){
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.hasVoted = hasVoted;
    }

    // Json parsing.
    public String toJSONString() {
        return genson.serialize(this);
    }

    public static Voter fromJSONString(String json) {
        Voter asset = genson.deserialize(json, Voter.class);
        return asset;
    }
}
