package chromatix.item;

public class ItemMusicDiscChirp extends ItemMusicDisc {
    public ItemMusicDiscChirp() {
        super(MUSIC_DISC_CHIRP);
    }

    @Override
    public String getSoundId() {
        return "record.chirp";
    }
}