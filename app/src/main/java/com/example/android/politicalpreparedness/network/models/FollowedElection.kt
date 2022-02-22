package com.example.android.politicalpreparedness.network.models

import androidx.room.*

@Entity(tableName = "followed_election_table")
data class FollowedElection(@PrimaryKey val id: Int) {
}