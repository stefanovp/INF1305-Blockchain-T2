/*
 * SPDX-License-Identifier: Apache-2.0
 */
package org;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;
import org.hyperledger.fabric.contract.annotation.Contact;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.License;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.util.ArrayList;
import java.util.List;

@Contract(name = "WeightedVotingContract", info = @Info(title = "WeightedVoting contract", description = "My Smart Contract", version = "0.0.1", license = @License(name = "Apache-2.0", url = ""), contact = @Contact(email = "WeightedVoting@example.com", name = "WeightedVoting", url = "http://WeightedVoting.me")))
@Default
public class WeightedVotingContract implements ContractInterface {
    public WeightedVotingContract() {
        // Empty.
    }

    @Transaction()
    public void init(Context ctx) {

        // Create sample voters.
        this.createVoter(ctx, "1", "Ana", 44);
        this.createVoter(ctx, "2", "Stefano", 11);
        this.createVoter(ctx, "3", "Lola", 33);

        // Create sample projects.
        this.createProject(ctx, "1", "BlockCard, a alternativa de credito", 0);
        this.createProject(ctx, "2", "ToyChain, ensine blockchain para criancas", 0);
    }

    // Utility. //
    @Transaction()
    public boolean assetExists(Context ctx, String key) {

        byte[] buffer = ctx.getStub().getState(key);
        return (buffer != null && buffer.length > 0);
    }

    // Voter CRUD. //
    @Transaction()
    public String createVoter(Context ctx, String voterId, String voterName, Integer voterWeight) {

        Voter createdVoter = new Voter();
        createdVoter.setId(voterId);
        createdVoter.setName(voterName);
        createdVoter.setWeight(voterWeight);

        String keyString = ctx.getStub().createCompositeKey("Voter", voterId).toString();
        boolean exists = assetExists(ctx, keyString);
        if (exists) {
            throw new RuntimeException("Failed to create voter. voter \"" + voterId + "\" already exists");
        }

        ctx.getStub().putState(keyString, createdVoter.toJSONString().getBytes(UTF_8));

        // Message to front end.
        return "Voter \"" + voterId + "\" succesfully created";
    }

    @Transaction()
    public Voter readVoter(Context ctx, String voterId) {

        String keyString = ctx.getStub().createCompositeKey("Voter", voterId).toString();
        boolean exists = assetExists(ctx, keyString);
        if (!exists) {
            throw new RuntimeException("Failed to read voter. Voter \"" + voterId + "\" does not exist");
        }

        Voter readVoter = Voter.fromJSONString(new String(ctx.getStub().getState(keyString), UTF_8));
        return readVoter;
    }

    @Transaction()
    public String updateVoter(Context ctx, String voterId, String voterName, Integer voterWeight) {

        Voter newVoter = new Voter();
        newVoter.setId(voterId);
        newVoter.setName(voterName);
        newVoter.setWeight(voterWeight);

        String keyString = ctx.getStub().createCompositeKey("Voter", voterId).toString();
        boolean exists = assetExists(ctx, keyString);
        if (!exists) {
            throw new RuntimeException("Failed to update voter. Voter \"" + voterId + "\" does not exist");
        }

        ctx.getStub().putState(keyString, newVoter.toJSONString().getBytes(UTF_8));

        // Message to front end.
        return "Voter \"" + voterId + "\" succesfully updated";
    }

    @Transaction()
    public String deleteVoter(Context ctx, String voterId) {

        String keyString = ctx.getStub().createCompositeKey("Voter", voterId).toString();
        boolean exists = assetExists(ctx, keyString);
        if (!exists) {
            throw new RuntimeException("Failed to delete voter. Voter \"" + voterId + "\" does not exist");
        }
        ctx.getStub().delState(keyString);

        // Message to front end.
        return "Voter \"" + voterId + "\" succesfully deleted";
    }

    // Project CRUD. //
    @Transaction()
    public String createProject(Context ctx, String projectId, String projectName, Integer projectVotes) {

        Project createdProject = new Project();
        createdProject.setId(projectId);
        createdProject.setName(projectName);
        createdProject.setVotes(projectVotes);

        String keyString = ctx.getStub().createCompositeKey("Project", projectId).toString();
        boolean exists = assetExists(ctx, keyString);
        if (exists) {
            throw new RuntimeException("Failed to create project. project \"" + projectId + "\" already exists");
        }

        ctx.getStub().putState(keyString, createdProject.toJSONString().getBytes(UTF_8));

        // Message to front end.
        return "Project \"" + projectId + "\" succesfully created";
    }

    @Transaction()
    public Project readProject(Context ctx, String projectId) {

        String keyString = ctx.getStub().createCompositeKey("Project", projectId).toString();
        boolean exists = assetExists(ctx, keyString);
        if (!exists) {
            throw new RuntimeException("Failed to read project. Project \"" + projectId + "\" does not exist");
        }

        Project readProject = Project.fromJSONString(new String(ctx.getStub().getState(keyString), UTF_8));
        return readProject;
    }

    @Transaction()
    public String updateProject(Context ctx, String projectId, String projectName, Integer projectVotes) {

        Project newProject = new Project();
        newProject.setId(projectId);
        newProject.setName(projectName);
        newProject.setVotes(projectVotes);

        String keyString = ctx.getStub().createCompositeKey("Project", projectId).toString();
        boolean exists = assetExists(ctx, keyString);
        if (!exists) {
            throw new RuntimeException("Failed to update project. Project \"" + projectId + "\" does not exist");
        }

        ctx.getStub().putState(keyString, newProject.toJSONString().getBytes(UTF_8));

        // Message to front end.
        return "Project \"" + projectId + "\" succesfully updated";
    }

    @Transaction()
    public String deleteProject(Context ctx, String projectId) {

        String keyString = ctx.getStub().createCompositeKey("Project", projectId).toString();
        boolean exists = assetExists(ctx, keyString);
        if (!exists) {
            throw new RuntimeException("Failed to delete project. Project \"" + projectId + "\" does not exist");
        }
        ctx.getStub().delState(keyString);

        // Message to front end.
        return "Project \"" + projectId + "\" succesfully deleted";
    }

    // Cast Vote.
    @Transaction()
    public String castVote(Context ctx, String voterId, String projectId) {

        // FALTA CHECKS DE EXISTENCIA.

        // Get voter and check if he's already voted.
        String voterKeyString = ctx.getStub().createCompositeKey("Voter", voterId).toString();

        Voter voter = Voter.fromJSONString(new String(ctx.getStub().getState(voterKeyString), UTF_8));
        if (voter.gethasVoted()) {
            throw new RuntimeException("Cannot cast vote. Voter \"" + voterId + "\" has already voted");
        }

        // Get project.
        String projectKeyString = ctx.getStub().createCompositeKey("Project", projectId).toString();
        Project project = Project.fromJSONString(new String(ctx.getStub().getState(projectKeyString), UTF_8));

        // Add votes to project.
        project.addVotes(voter.getWeight());

        // Set voter vote status to "already voted"; Voted = true.
        voter.sethasVoted(true);

        // Update project votes and
        // Update voter vote status in world state.
        ctx.getStub().putState(projectKeyString, project.toJSONString().getBytes(UTF_8));
        ctx.getStub().putState(voterKeyString, voter.toJSONString().getBytes(UTF_8));

        // Message to front end.
        return "Voter \"" + voter.getName() + "\" votes added to project \"" + project.getName() + "\"";
    }

    // Queries //
    @Transaction()
    public String[] queryAllVoters(Context ctx) {

        List<String> voterList = new ArrayList<String>();
        QueryResultsIterator<KeyValue> voters = ctx.getStub().getStateByPartialCompositeKey("Voter");

        for (org.hyperledger.fabric.shim.ledger.KeyValue keyValue : voters) {
            System.out.println("voter:");
            System.out.println(keyValue.getStringValue());
            
            voterList.add(keyValue.getStringValue());
        }

        return voterList.toArray(new String[voterList.size()]);
    }

    @Transaction()
    public String[] queryAllProjects(Context ctx){
        
        List<String> projectList = new ArrayList<String>();
        QueryResultsIterator<KeyValue> projects = ctx.getStub().getStateByPartialCompositeKey("Project");
        for (KeyValue keyValue : projects) {

            System.out.println("project:");
            System.out.println(keyValue.getStringValue());
            projectList.add(keyValue.getStringValue());
        }

        return projectList.toArray(new String[projectList.size()]);
    }
}
