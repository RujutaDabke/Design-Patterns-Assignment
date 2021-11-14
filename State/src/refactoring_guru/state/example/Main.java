package refactoring_guru.state.example;
        import javax.swing.*;
        import java.awt.*;
        import java.util.ArrayList;
        import java.util.List;
class ui {
    private refactoring_guru.state.example.player player;
    private static JTextField textField = new JTextField();
    public ui(refactoring_guru.state.example.player player) {
        this.player = player;
    }
    public void init() {
        JFrame frame = new JFrame("Test player");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel context = new JPanel();
        context.setLayout(new BoxLayout(context, BoxLayout.Y_AXIS));
        frame.getContentPane().add(context);
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        context.add(textField);
        context.add(buttons);
        JButton play = new JButton("Play");
        play.addActionListener(e -> textField.setText(player.getState().onPlay()));
        JButton stop = new JButton("Stop");
        stop.addActionListener(e -> textField.setText(player.getState().onLock()));
        JButton next = new JButton("Next");
        next.addActionListener(e -> textField.setText(player.getState().onNext()));
        JButton prev = new JButton("Prev");
        prev.addActionListener(e -> textField.setText(player.getState().onPrevious()));
        frame.setVisible(true);
        frame.setSize(300, 100);
        buttons.add(play);
        buttons.add(stop);
        buttons.add(next);
        buttons.add(prev);
    }
}
class Demo {
    public static void main(String[] args) {
        player player = new player();
        ui ui = new ui(player);
        ui.init();
    }
}
abstract class state {
    refactoring_guru.state.example.player player;
    state(refactoring_guru.state.example.player player) {
        this.player = player;
    }
    public abstract String onLock();
    public abstract String onPlay();
    public abstract String onNext();
    public abstract String onPrevious();
}
class player {
    private refactoring_guru.state.example.state state;
    private boolean playing = false;
    private List<String> playlist = new ArrayList<>();
    private int currentTrack = 0;
    public player() {
        this.state = new readyState(this);
        setPlaying(true);
        for (int i = 1; i <= 12; i++) {
            playlist.add("Track " + i);
        }
    }
    public void changeState(refactoring_guru.state.example.state state) {
        this.state = state;
    }
    public refactoring_guru.state.example.state getState() {
        return state;
    }
    public void setPlaying(boolean playing) {
        this.playing = playing;
    }
    public boolean isPlaying() {
        return playing;
    }
    public String startPlayback() {
        return "Playing " + playlist.get(currentTrack);
    }
    public String nextTrack() {
        currentTrack++;
        if (currentTrack > playlist.size() - 1) {
            currentTrack = 0;
        }
        return "Playing " + playlist.get(currentTrack);
    }
    public String previousTrack() {
        currentTrack--;
        if (currentTrack < 0) {
            currentTrack = playlist.size() - 1;
        }
        return "Playing " + playlist.get(currentTrack);
    }
    public void setCurrentTrackAfterStop() {
        this.currentTrack = 0;
    }
}
class readyState extends state {
    public readyState(refactoring_guru.state.example.player player) {
        super(player);
    }
    @Override
    public String onLock() {
        player.changeState(new lockedState(player));
        return "Locked...";
    }
    @Override
    public String onPlay() {
        String action = player.startPlayback();
        player.changeState(new playingstate(player));
        return action;
    }
    @Override
    public String onNext() {
        return "Locked...";
    }
    @Override
    public String onPrevious() {
        return "Locked...";
    }
}
class playingstate extends state {
    playingstate(refactoring_guru.state.example.player player) {
        super(player);
    }
    @Override
    public String onLock() {
        player.changeState(new lockedState(player));
        player.setCurrentTrackAfterStop();
        return "Stop playing";
    }
    @Override
    public String onPlay() {
        player.changeState(new readyState(player));
        return "Paused...";
    }
    @Override
    public String onNext() {
        return player.nextTrack();
    }
    @Override
    public String onPrevious() {
        return player.previousTrack();
    }
}
class lockedState extends state {
    lockedState(refactoring_guru.state.example.player player) {
        super(player);
        player.setPlaying(false);
    }
    @Override
    public String onLock() {
        if (player.isPlaying()) {
            player.changeState(new readyState(player));
            return "Stop playing";
        } else {
            return "Locked...";
        }
    }
    @Override
    public String onPlay() {
        player.changeState(new readyState(player));
        return "Ready";
    }
    @Override
    public String onNext() {
        return "Locked...";
    }
    @Override
    public String onPrevious() {
        return "Locked...";
    }
}