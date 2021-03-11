package me.ifamasssxd.cosmicsky.inventorypets.struct;

import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import java.util.Arrays;
import java.util.UUID;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.ifamasssxd.cosmicsky.inventorypets.struct.pets.*;
import net.minecraft.server.v1_8_R3.GameProfileSerializer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public enum InventoryPets {
   LAVA(CC.YellowB + "Lava Elemental Pet", CC.YellowB + "Lava Pet", new InventoryLavaPet(), "eyJ0aW1lc3RhbXAiOjE1NTk2ODAxMjI4ODIsInByb2ZpbGVJZCI6ImZjMWJiM2RkMTA4ZTRlNDliMDBlYjEyMDFmYmJhYWNlIiwicHJvZmlsZU5hbWUiOiJMYXZhIiwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzM4NjgwNDE2ZjBiOTNkYjNlMDRlZDgzNzU2MWRhYzk2YTI0OGExZTM4YmQ4YjNlNmEzYzIwY2ZiYWI3MjUyODgifX19"),
   WATER(CC.YellowB + "Water Elemental Pet", CC.YellowB + "Water Pet", new InventoryWaterPet(), "eyJ0aW1lc3RhbXAiOjE1NTk2Nzk5NDQ5NzQsInByb2ZpbGVJZCI6IjdjODk1YWQwMTFkMDQzNTA5YWU1ZjJiYjFjZjZjOGVhIiwicHJvZmlsZU5hbWUiOiJCYXNpY0JBRSIsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS83YjBhZjJhOWNkOWExYTk2MTU3M2ZhZWE2NGFmYzhlYjZiN2U3NTdkZjMwZDdmNWY3ZWUyZGJmYzFmYmUzNGRiIn19fQ=="),
   FEIGN_DEATH(CC.YellowB + "Feign Death Pet", CC.YellowB + "Feign Death Pet", new InventoryFeignDeathPet(), "eyJ0aW1lc3RhbXAiOjE1NTk2ODAwNTI5MDcsInByb2ZpbGVJZCI6IjdjODk1YWQwMTFkMDQzNTA5YWU1ZjJiYjFjZjZjOGVhIiwicHJvZmlsZU5hbWUiOiJCYXNpY0JBRSIsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS81NjBhMDE0ZDhjOTAyMzVmNzc1Y2Y5OTljY2M2ODM2NWQ3OGM2YTM2NjI0MDM5MjBlNjQ5NjM0MjZlY2IyNDc1In19fQ=="),
   EVOLUTION(CC.YellowB + "Evolution Pet", CC.YellowB + "Evolution Pet", new InventoryEvolutionPet(), "eyJ0aW1lc3RhbXAiOjE1NjAyMTE1NDU5NDUsInByb2ZpbGVJZCI6IjdjODk1YWQwMTFkMDQzNTA5YWU1ZjJiYjFjZjZjOGVhIiwicHJvZmlsZU5hbWUiOiJCYXNpY0JBRSIsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9lMDcxMThiNGU5ZDFiMTkyN2QwOTE4ZWM1Y2E5ZDBjNWNjYTNmZjMxZmI4OWNhNmQ1NDJiMTY3YzE5MTIxZjI0In19fQ=="),
   ANTI_TELEBLOCK(CC.YellowB + "Anti Teleblock Pet", CC.YellowB + "Anti Teleblock Pet", new InventoryAntiTeleblockPet(), "eyJ0aW1lc3RhbXAiOjE1NjAyMTE0NDQ0NTksInByb2ZpbGVJZCI6IjdjODk1YWQwMTFkMDQzNTA5YWU1ZjJiYjFjZjZjOGVhIiwicHJvZmlsZU5hbWUiOiJCYXNpY0JBRSIsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8xYjA5YTM3NTI1MTBlOTE0YjBiZGM5MDk2YjM5MmJiMzU5ZjdhOGU4YTk1NjZhMDJlN2Y2NmZhZmY4ZDZmODllIn19fQ=="),
   BANNER(CC.YellowB + "Banner Pet", CC.YellowB + "Banner Pet", new InventoryBannerPet(), "eyJ0aW1lc3RhbXAiOjE1NjAyMTE1OTI3MzEsInByb2ZpbGVJZCI6IjdjODk1YWQwMTFkMDQzNTA5YWU1ZjJiYjFjZjZjOGVhIiwicHJvZmlsZU5hbWUiOiJCYXNpY0JBRSIsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8xZTVlOTlhMjcwM2M2Y2YzYTQyNDU2Zjg3YzdlZTc5NzNiNzJmN2VhMDI1Njk4YzA2ZTVmZGVmOGE4NDhhZDllIn19fQ=="),
   XP_BOOSTER(CC.YellowB + "XP Booster Pet", CC.YellowB + "XP Booster", new InventoryPetXpBooster(), "eyJ0aW1lc3RhbXAiOjE1NjA5NzQ0ODY1MDIsInByb2ZpbGVJZCI6IjdjODk1YWQwMTFkMDQzNTA5YWU1ZjJiYjFjZjZjOGVhIiwicHJvZmlsZU5hbWUiOiJCYXNpY0JBRSIsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS84NzNkNTU4MzUyMmZkYTU3ZGQ1MzMwZjY1NmZjZjM2MjQ0Y2YwNDBkNjA0ODM0YTkxNzkwMTlkNjhhMzVhZmIxIn19fQ=="),
   TESLA(CC.YellowB + "Tesla Pet", CC.YellowB + "Tesla", new InventoryPetTesla(), "eyJ0aW1lc3RhbXAiOjE1NjA5NzQzNTY4NzgsInByb2ZpbGVJZCI6IjdjODk1YWQwMTFkMDQzNTA5YWU1ZjJiYjFjZjZjOGVhIiwicHJvZmlsZU5hbWUiOiJCYXNpY0JBRSIsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS85YmY3MjI1YzI2ODA0NWIyZTFjMTM0ODgzNDk1Y2UzYWY2MmFhOTc4ZGVmNzFkNTMwMWQ5YTg4MzFjM2M0ZDlhIn19fQ=="),
   BLACKSCROLL(CC.YellowB + "Blackscroll Pet", CC.YellowB + "Blackscroll", new InventoryBlackscrollPet(), "eyJ0aW1lc3RhbXAiOjE1NjE1OTM4MTMxMDYsInByb2ZpbGVJZCI6IjdjODk1YWQwMTFkMDQzNTA5YWU1ZjJiYjFjZjZjOGVhIiwicHJvZmlsZU5hbWUiOiJCYXNpY0JBRSIsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8yNzY4MGE4MjMxNmNhNjc5YmI2ZDc0YTQzZTU4ZTUzMWI4Mjg2NzhkNzU5ZDRkYzIxMDlkZjY5ODI0YjZiYmI3In19fQ=="),
   ALCHEMIST(CC.YellowB + "Alchemist Pet", CC.YellowB + "Alchemist", new InventoryAlchemistPet(), "eyJ0aW1lc3RhbXAiOjE1NjE1OTM2NzA4NzAsInByb2ZpbGVJZCI6IjdjODk1YWQwMTFkMDQzNTA5YWU1ZjJiYjFjZjZjOGVhIiwicHJvZmlsZU5hbWUiOiJCYXNpY0JBRSIsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8zMjU1MzI3ZGQ4ZTkwYWZhZDY4MWExOTIzMTY2NWJlYTJiZDA2MDY1YTA5ZDc3YWMxNDA4ODM3ZjllMGIyNDIifX19"),
   GAIA(CC.YellowB + "Gaia Pet", CC.YellowB + "Gaia", new InventoryGaiaPet(), "eyJ0aW1lc3RhbXAiOjE1NjIwOTAxODY4OTYsInByb2ZpbGVJZCI6IjdjODk1YWQwMTFkMDQzNTA5YWU1ZjJiYjFjZjZjOGVhIiwicHJvZmlsZU5hbWUiOiJCYXNpY0JBRSIsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9jY2UzYTc0MjdiY2IwNTRlZjFhNzY0NGY2Y2EwZWQwZWRjOTY3NDdhYWJjZjJlMWQ1ZjVmNjc3YjhkZGY2NDA3In19fQ=="),
   ENCHANTER(CC.YellowB + "Enchanter Pet", CC.YellowB + "Enchanter", new InventoryEnchanterPet(), "eyJ0aW1lc3RhbXAiOjE1NjIwODkwMjE1NjEsInByb2ZpbGVJZCI6IjdjODk1YWQwMTFkMDQzNTA5YWU1ZjJiYjFjZjZjOGVhIiwicHJvZmlsZU5hbWUiOiJCYXNpY0JBRSIsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS82YTdmNjUyNjFjYTlmYWZhMTA1ZmRkM2UzNWY4NGQyMmE5Y2IxZmJkNTJjODRhZjdkZmM1NmM3ZDFkZmMzYjRmIn19fQ=="),
   STRONGHOLD(CC.YellowB + "Stronghold Sell Pet", CC.YellowB + "Stronghold Sell", new InventoryStrongholdPet(), "eyJ0aW1lc3RhbXAiOjE1NjI2Mjc1MDU4MjUsInByb2ZpbGVJZCI6IjdjODk1YWQwMTFkMDQzNTA5YWU1ZjJiYjFjZjZjOGVhIiwicHJvZmlsZU5hbWUiOiJCYXNpY0JBRSIsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS84MDExOGJhZjhhZTFiYWM2Mjc1ZDdiNzE5YzdjZDg5YzU4ZDcwMmI0ZmEyOWQ2ZjhiY2NkYmYwYzRkM2ZiNWY2In19fQ=="),
   RAID_CREEPER(CC.YellowB + "Raid Creeper Pet", CC.YellowB + "Raid Creeper", new InventoryRaidCreeperPet(), "eyJ0aW1lc3RhbXAiOjE1NjI2MjczMjIzMTIsInByb2ZpbGVJZCI6IjdjODk1YWQwMTFkMDQzNTA5YWU1ZjJiYjFjZjZjOGVhIiwicHJvZmlsZU5hbWUiOiJCYXNpY0JBRSIsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS81MDg0ZWVlY2UxOTM4ZjViZGY3M2ViNjY4YzBhOTEwZDU5Y2UzYjRkMzExODViN2VkNzUyNTQxYjFiN2ZmZmY3In19fQ=="),
   VILE_CREEPER(CC.YellowB + "Vile Creeper Pet", CC.YellowB + "Vile Creeper", new InventoryVileCreeperPet(), "eyJ0aW1lc3RhbXAiOjE1NjMyNzczNjY1MDYsInByb2ZpbGVJZCI6IjdjODk1YWQwMTFkMDQzNTA5YWU1ZjJiYjFjZjZjOGVhIiwicHJvZmlsZU5hbWUiOiJCYXNpY0JBRSIsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS80YjA2OWNlY2Y0MjNjMzFiNDQ0ZTE4MGVlNGU3ODcyZDBhMmNkZjM0YjQ4YzNkMzI3MGJlZmY1MmM3MjRhOWIzIn19fQ=="),
   SMITE(CC.YellowB + "Smite Pet", CC.YellowB + "Smite", new InventorySmitePet(), "eyJ0aW1lc3RhbXAiOjE1NjMyNzc0OTc4MTUsInByb2ZpbGVJZCI6IjdjODk1YWQwMTFkMDQzNTA5YWU1ZjJiYjFjZjZjOGVhIiwicHJvZmlsZU5hbWUiOiJCYXNpY0JBRSIsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9lMDU3OGFkN2FiYjE1ZDBlNmIyNTQ1MzE3YTQ3ZWUwNWZmMWM0MTM4NzJkMGJhNTEzOWEyMDhhNzA2MTM3ZjBlIn19fQ=="),
   BLESS(CC.YellowB + "Bless Pet", CC.YellowB + "Bless", new InventoryBlessPet(), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMDlhNTJjYjUwOTkyZDgzYzU1OTlmZDZlNDFhNmNlOTljZjdmMWU2MjAzNjExOTYzZGMyYzJmZGEwYjU1NTgzIn19fQ=="),
   ARCANE_CREEPER(CC.YellowB + "Arcane Creeper Pet", CC.YellowB + "Arcane Creeper", new InventoryArcaneCreeperPet(), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWM3YzZmZTM0NGQ5NzBmYTI3MzRkYzQzNDYzZjkxYTYxYmM3N2I3Y2QxMzJkNThjYjg2MzI1OGEzMjc2YSJ9fX0="),
   WORLD_DESTROYER(CC.YellowB + "World Destroyer Pet", CC.YellowB + "World Destroyer", new InventoryWorldDestroyerPet(), "eyJ0aW1lc3RhbXAiOjE1NjYyNDY4MTk4MDIsInByb2ZpbGVJZCI6IjdjODk1YWQwMTFkMDQzNTA5YWU1ZjJiYjFjZjZjOGVhIiwicHJvZmlsZU5hbWUiOiJCYXNpY0JBRSIsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS80MjQ4NjAwM2U4NTQ1OThiODAxZjA3ZDc2NjI2MDBjZmJkNmY0MGVmZTcyYjI2MmI1Mjc5NGNjYWRhNTlhMWE2In19fQ="),
   ABOMINABLE_SNOWMANPET(CC.YellowB + "Abominable Snowman Pet", CC.YellowB + "Abominable Snowman", new InventoryAbominableSnowmanPet(), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTBhZTkxODQxYWZkMjQzZWMxOTgyYTc5ZmM0NTgyM2I0ZDM3NGY2MjFlMGQ3MzQ1MzNmODZkNzI1YTZmMGE0ZiJ9fX0="),
   BANSHEE(CC.DarkRedB + "Banshee Pet", CC.DarkRed + "Banshee", new InventoryBansheePet(), "eyJ0aW1lc3RhbXAiOjE1Njc1NDcyOTgzODIsInByb2ZpbGVJZCI6IjdjODk1YWQwMTFkMDQzNTA5YWU1ZjJiYjFjZjZjOGVhIiwicHJvZmlsZU5hbWUiOiJCYXNpY0JBRSIsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS80MTU2OWUyZGRjNzcxODFhZDBkNGY4MzA5YzQ3MDU4M2M0MjI1ZTJlY2M3NWNlMzBjZjc2NTAxNDg5MzhmNDg4In19fQ="),
   TITAN(CC.DarkRedB + "Titan Pet", CC.DarkRed + "Titan", new InventoryTitanPet(), "eyJ0aW1lc3RhbXAiOjE1NTg2MTg0ODc0ODcsInByb2ZpbGVJZCI6ImIwZDRiMjhiYzFkNzQ4ODlhZjBlODY2MWNlZTk2YWFiIiwicHJvZmlsZU5hbWUiOiJ4RmFpaUxlUiIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDM4M2JhOWY0YzIxZTVjYjMzOTljMDY1MjI1NGYxZDljN2NmMmViNDY3NjdiM2U5MmY2ZThmNzc3YmZhNzM2OSJ9fX0="),
   BLOODHOUND(CC.DarkRedB + "Bloodhound Pet", CC.DarkRed + "Bloodhound", new InventoryBloodhoundPet(), "eyJ0aW1lc3RhbXAiOjE1NzU5NTEyNzk2NjQsInByb2ZpbGVJZCI6IjIzZjFhNTlmNDY5YjQzZGRiZGI1MzdiZmVjMTA0NzFmIiwicHJvZmlsZU5hbWUiOiIyODA3Iiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9lYTI5NmY4ZTEwZWM3Mjk2MTlkNjczMjczYTM4MTMzNThiMTEyMGFiYTExYTU4MDM0ZjQxOGI5NjFjNjY5N2UwIn19fQ="),
   SKRRT(CC.DarkRedB + "Skrrt Pet", CC.DarkRed + "Skrrt", new InventorySkrrtPet(), "eyJ0aW1lc3RhbXAiOjE1NzU5MjYyMDQwMTksInByb2ZpbGVJZCI6Ijc1MTQ0NDgxOTFlNjQ1NDY4Yzk3MzlhNmUzOTU3YmViIiwicHJvZmlsZU5hbWUiOiJUaGFua3NNb2phbmciLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzJjMzk2MWQxOGNhMGI2MGY5NTMzZjY1NGQ4NmZmNTE1ZDJjOTM4M2VhNDU5NDBkMjJhMmYxMDRjN2MxNmFhMjYiLCJtZXRhZGF0YSI6eyJtb2RlbCI6InNsaW0ifX19fQ="),
   YIJKI_CONTROLLER(CC.YellowB + "Yijki Controller Pet", CC.YellowB + "Yijki Controller", new InventoryYijkiControllerPet(), "eyJ0aW1lc3RhbXAiOjE1NjM4NDcwMzA3OTQsInByb2ZpbGVJZCI6IjdjODk1YWQwMTFkMDQzNTA5YWU1ZjJiYjFjZjZjOGVhIiwicHJvZmlsZU5hbWUiOiJCYXNpY0JBRSIsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9hMWVkM2YzN2Q4ZTkwNjNkY2VlYTI0NTQ3M2U2ZDljYzk2ODliYzM0NWFlMjFlYTE1NTk4ZGU1NGE0NDY0N2Y4In19fQ=="),
   WITCHER(CC.DarkRedB + "Witcher Pet", CC.DarkRedB + "Witcher", new InventoryWitcherPet(), "ewogICJ0aW1lc3RhbXAiIDogMTU4OTQyNjcxOTI1OCwKICAicHJvZmlsZUlkIiA6ICI3Yzg5NWFkMDExZDA0MzUwOWFlNWYyYmIxY2Y2YzhlYSIsCiAgInByb2ZpbGVOYW1lIiA6ICJCYXNpY0JBRSIsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS84MTc4Y2YyYTY2MzFlNzlhODMyN2MyMTkyZGU2YTgzNjAzODQyZDlmZTNhMTE1ZGI5Y2JmOWJlZmE1NTZjYTNhIgogICAgfQogIH0KfQ"),
   DEMONIC(CC.YellowB + "Demonic Impling Pet", CC.Yellow + "Demonic Impling", new InventoryImplingPet(), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWMyOTc4NDRhNzIyY2M4MGM3NDcxNTJiMjk2Y2Q3NTEzMzhkMzYzMjcxOTQxZmI3ZmIzZWJiOTNjOWFjZjI3In19fQ=="),
   FAIRY(CC.YellowB + "Aether Fairy Pet", CC.YellowB + "Aether Fairy", new InventoryFairyPet(), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjVhNTQxNmRiNmIzNDU3MzY4YWFhMDFmNGU4OWJjMjk2ODU5YzE5NWI4OTg5NTFlNjFjNTIwN2NlMzVkZDkifX19"),
   STITCHES(CC.YellowB + "Stitches Pet", CC.YellowB + "Stitches" , new InventoryStitchesPet(), "eyJ0aW1lc3RhbXAiOjE1NzIzMDU5MDY3NDgsInByb2ZpbGVJZCI6IjdjODk1YWQwMTFkMDQzNTA5YWU1ZjJiYjFjZjZjOGVhIiwicHJvZmlsZU5hbWUiOiJCYXNpY0JBRSIsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS82NTU1ZGY5NjkzOGMyYjc1MjI3YWYzYTVhOTI0MjdjZTU5MDFjNjhhZmE4OTBlYWVmOWNlM2QyMmU1MTFhZjFjIn19fQ==" )

   ;

   private String itemName;
   private String petName;
   private InventoryPet internalClass;
   private String skinBlob;
   private transient NBTTagCompoundBuilder builder;

   private InventoryPets(String itemName, String petName, InventoryPet instance, String blob) {
      this.itemName = itemName;
      this.petName = petName;
      this.internalClass = instance;
      this.skinBlob = blob;
      GameProfile profile = new GameProfile(UUID.randomUUID(), this.name().toLowerCase());
      profile.getProperties().put("textures", new Property("textures", this.skinBlob));
      this.builder = new NBTTagCompoundBuilder();
      GameProfileSerializer.serialize(this.builder.get(), profile);
   }


   public String getMsgWithPrefix() {
      return this.getMsgWithPrefix(CC.Red);
   }

   public String getMsgWithPrefix(String color) {
      return color + CC.Bold + "PET (" + color + ChatColor.stripColor(this.getPetName()) + color + CC.Bold + ")" + color + ":" + CC.White;
   }

   public void sendMsg(Player player, String color, String msg) {
      player.sendMessage(this.getMsgWithPrefix(color) + " " + msg);
   }

   public void sendMsg(Player player, String msg) {
      this.sendMsg(player, CC.Red, msg);
   }

   public static InventoryPets fromName(String name) {
      return (InventoryPets)Arrays.stream(values()).filter((f) -> {
         return f.name().equalsIgnoreCase(name);
      }).findFirst().orElse(null);
   }

   private InventoryPets(String s, int n, String itemName, String petName, InventoryPet internalClass, String skinBlob, NBTTagCompoundBuilder builder) {
      this.itemName = itemName;
      this.petName = petName;
      this.internalClass = internalClass;
      this.skinBlob = skinBlob;
      this.builder = builder;
   }

   public String getItemName() {
      return this.itemName;
   }

   public String getPetName() {
      return this.petName;
   }

   public InventoryPet getInternalClass() {
      return this.internalClass;
   }

   public String getSkinBlob() {
      return this.skinBlob;
   }

   public NBTTagCompoundBuilder getBuilder() {
      return this.builder;
   }
}
