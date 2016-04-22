package fur.pong.server;

import fur.pong.common.msg.Input;
import fur.pong.common.msg.State;
import fur.pong.server.collections.CircularBuffer;
import fur.pong.server.collections.InputTuple;

public class PhysEngine {

    private final static int LEN = 400;
    private final static int STEP = 30; // ms

    public CircularBuffer<State> state = new CircularBuffer<>(State.class, LEN); // should be enough for 10 seconds
    public CircularBuffer<InputTuple> inputs = new CircularBuffer<>(InputTuple.class, LEN);

    int beginBucket = 0;

    public PhysEngine() {

    }

    public State compute(int ms) {
        int msBucket = mTb(ms);
        assert msBucket < LEN + beginBucket;
        if (state.getLen() < msBucket) {
            int l = state.getLen();
            for (int i = l; i <= msBucket; i++) {
                state.set(state.getEarlier(i), i); // TODO: simulate
            }
        }
        return state.get(msBucket);
    }

    public void setInputs(Iterable<Input> inputs, boolean isFirstPlayer) {
        for (Input input : inputs) {
            setInput(input, isFirstPlayer);
        }
    }

    public void setInput(Input input, boolean isFirstPlayer) {
        int inputTime = mTb(input.ms);
        assert inputTime < LEN + beginBucket;
        InputTuple tuple = inputs.get(inputTime);
        if (tuple == null)
            tuple = new InputTuple();
        if (isFirstPlayer)
            tuple.setX(input);
        else
            tuple.setY(input);
        int lenBefore = inputs.getLen();
        inputs.set(tuple, inputTime);
        int lenDelta = inputs.getLen() - lenBefore;
        inputs.shift(lenDelta);
        beginBucket += lenDelta;
    }

    public int getStartTime() {
        return beginBucket*STEP;
    }

    private int bTm(int bucket) {
        return bucket*STEP + beginBucket;
    }

    private int mTb(int bucket) {
        return bucket/STEP - beginBucket;
    }

    @Override
    public String toString() {
        return "beginBucket=" + beginBucket + "(" + bTm(beginBucket) + ")\nState: " + state + "\nInputs: " + inputs;
    }

    public String toStringState() {
        return "beginBucket=" + beginBucket + "(" + bTm(beginBucket) + ") State: " + state.toStringState() + " Inputs: " + inputs.toStringState();
    }
}
