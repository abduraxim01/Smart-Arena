package com.practise.Smart_Arena.service.PlayerService;

import com.practise.Smart_Arena.exception.AllExceptions;
import com.practise.Smart_Arena.model.owner.Status;
import com.practise.Smart_Arena.model.player.PlayerMatchStats;
import com.practise.Smart_Arena.repository.PlayerMatchStatsRepository;
import com.practise.Smart_Arena.repository.PlayerRepository;
import com.practise.Smart_Arena.repository.StatusRepository;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class MarkAsAttendService implements CommandLineRunner {

    final private StatusRepository statusRep;

    final private PlayerRepository playerRep;

    final private PlayerMatchStatsRepository playerMatchStatsRep;

    final private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Autowired
    public MarkAsAttendService(StatusRepository statusRep, PlayerRepository playerRep, PlayerMatchStatsRepository playerMatchStatsRep) {
        this.statusRep = statusRep;
        this.playerRep = playerRep;
        this.playerMatchStatsRep = playerMatchStatsRep;
    }

    @Override
    public void run(String... args) {
        scheduler.scheduleAtFixedRate(this::checkAndUpdateMatches, 0, 1, TimeUnit.MINUTES);
    }

    @PreDestroy
    public void stop() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(10, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private void checkAndUpdateMatches() {
        List<Status> statusList = statusRep.findAll();
        statusList.forEach(this::markAsAttend);
    }

    private void markAsAttend(Status status) {
        PlayerMatchStats stats = playerRep.findById(status.getPlayer().getId()).orElseThrow(() -> new AllExceptions.EntityNotFoundException("Player not found"))
                .getPlayerMatchStats();
        stats.setAttended(stats.getAttended() + 1);
        playerMatchStatsRep.save(stats);
    }
}
