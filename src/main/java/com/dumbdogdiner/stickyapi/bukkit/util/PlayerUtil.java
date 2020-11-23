/**
 * Copyright (c) 2020 DumbDogDiner <a href="dumbdogdiner.com">&lt;dumbdogdiner.com&gt;</a>. All rights reserved.
 * Licensed under the MIT license, see LICENSE for more information...
 */
package com.dumbdogdiner.stickyapi.bukkit.util;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;

import static com.dumbdogdiner.stickyapi.common.util.NumberUtil.intHelper;


//FIXME we need to have a login logout listener, and tbh this should be done by a DB, and this class should act as a basic db interface or similar. Probably need a combined waterfall-bukkit plugin for this, or maybe just waterfall. I honestly do not know.
@SuppressWarnings("unused")
public class PlayerUtil {
    public static class Names {
        static List<String> allPlayerNames;
        static {
            allPlayerNames = new ArrayList<>();
            OfflinePlayer [] offlinePlayers = Bukkit.getServer().getOfflinePlayers();
            Arrays.sort(offlinePlayers, new OfflinePlayerSeenComparator());

            for (OfflinePlayer player : offlinePlayers) {
                allPlayerNames.add(player.getName());
            }

            allPlayerNames.addAll(0, getOnlinePlayers());
        }
        public static List<String> getOnlinePlayers() {
            ArrayList<String> playerNames = new ArrayList<>();

            List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
            //players.sort(new OfflinePlayerSeenComparator());
            players.forEach(player -> {
                playerNames.remove(player.getName());
            });

            allPlayerNames.removeAll(playerNames);
            playerNames.sort(String.CASE_INSENSITIVE_ORDER);
            return playerNames;
        }

        public static List<String> getOfflinePlayers() {
            List<String> playerNames = getAllPlayers();

            Bukkit.getOnlinePlayers().forEach(player -> {
                playerNames.remove(player.getName());
            });
            return playerNames;
        }

        public static List<String> getAllPlayers() {
            getOnlinePlayers();
            return allPlayerNames;
        }
    }

    public static class OfflinePlayerSeenComparator implements Comparator<OfflinePlayer> {

        /**
         * Bigger number means seen more recently, which means we want it earlier in the list, so return -1
         * if o1 has a bigger number, we should return a negative number
         * if o2 has a bigger number, we should return a positive number
         * Check the following, in order, break on non-tie:
         * lastSeen, lastLogin, firstLogin, compare names as strings
         * if still a tie just return 0
         * @param o1 The first {@link org.bukkit.OfflinePlayer}
         * @param o2 The second {@link org.bukkit.OfflinePlayer}
         * @return An integer representing if o1 is less than, equal to, or greater than o2
         */
        @Override
        public int compare(OfflinePlayer o1, OfflinePlayer o2) {


            long [] diffs = new long[] {
                    o2.getLastSeen() - o1.getLastSeen(),
                    o2.getLastLogin() - o1.getLastLogin(),
                    o2.getFirstPlayed() - o1.getFirstPlayed()
            };

            for(long diff : diffs) {
                if (diff != 0) {
                    return intHelper(diff);
                }
            }
            return Objects.requireNonNull(o1.getName()).compareTo(Objects.requireNonNull(o2.getName()));
        }


    }
}
