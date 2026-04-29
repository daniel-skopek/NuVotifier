package com.vexsoftware.votifier.model;

/**
 * {@code VotifierEvent} is a custom event class that represents a vote.
 * This is the base event class that can be extended by platform-specific implementations.
 *
 * @author frelling
 */
public class VotifierEvent {
    /**
     * Encapsulated vote record.
     */
    private final Vote vote;

    /**
     * Constructs a vote event that encapsulated the given vote record.
     *
     * @param vote vote record
     */
    public VotifierEvent(final Vote vote) {
        this.vote = vote;
    }

    /**
     * Return the encapsulated vote record.
     *
     * @return vote record
     */
    public Vote getVote() {
        return vote;
    }
}
