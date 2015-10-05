package com.bignerdranch.android.pg1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private static final String EXTRA_SCORE = "com.bignerdranch.android.pg1.score",
                                EXTRA_DIFFICULTY_IS_HARD = "com.bignerdranch.android.pg1.difficulty_is_hard",
                                KEY_SCORE = "score",
                                KEY_MILLIS_UNTIL_FINISHED = "millis until finished",
                                TAG = "GameActivity";

    private int mScore;
    private long mMillisUntilFinished;
    private boolean mDifficultyIsHard;

    // Create a new intent of this activity, passing in the difficulty level.
    public static Intent newIntent(Context packageContext, boolean difficultyIsHard) {
        Intent i = new Intent(packageContext, GameActivity.class);
        i.putExtra(EXTRA_DIFFICULTY_IS_HARD, difficultyIsHard);
        return i;
    }

    public static int getScore(Intent i) {
        return i.getIntExtra(EXTRA_SCORE, 0);
    }

    // Sends back to the parent activity the player's score.
    private void setReturnValues(int score) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(EXTRA_SCORE, score);
        setResult(RESULT_OK, returnIntent);
    }

    private boolean getDifficulty() {
        return getIntent().getBooleanExtra(EXTRA_DIFFICULTY_IS_HARD, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mDifficultyIsHard = getDifficulty();

        if( savedInstanceState != null) {
            mScore = savedInstanceState.getInt(KEY_SCORE);
            mMillisUntilFinished = savedInstanceState.getLong(KEY_MILLIS_UNTIL_FINISHED);
        } else {
            mScore = 0;
            mMillisUntilFinished = 10000;
        }

        GameDraw mGameDraw = new GameDraw(this);
        setContentView(mGameDraw);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_SCORE, mScore);
        savedInstanceState.putLong(KEY_MILLIS_UNTIL_FINISHED, mMillisUntilFinished);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // The game interface. 20 circles of slightly varying sizes (smaller sizes on hard) are on the
    // screen at all times. Score is incremented when the player touches one of the circles. Said
    // circle is then redrawn elsewhere on the screen. The game ends after 10 seconds.
    public class GameDraw extends View implements View.OnTouchListener {
        private static final String TAG = "DrawView";

        private int mScreenWidth, mScreenHeight, mRadius;
        private String mTimerText;
        private Circle[] mCircles = new Circle[20];
        private Random mRandom = new Random();
        private Paint mPaint = new Paint();

        public GameDraw(Context context) {
            super(context);
            setFocusable(true);
            setFocusableInTouchMode(true);
            this.setOnTouchListener(this);

            mRadius = mDifficultyIsHard ? 80 : 150;
            mTimerText = String.format(getResources().getString(R.string.timer), 30.0);

            CountDownTimer mTimer = new CountDownTimer(mMillisUntilFinished, 1) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mMillisUntilFinished = millisUntilFinished;
                    mTimerText = String.format(getResources().getString(R.string.timer), millisUntilFinished/1000.0);
                    invalidate();
                }

                @Override
                public void onFinish() {
                    setReturnValues(mScore);
                    finish();
                }
            };

            mTimer.start();

            mPaint.setTextAlign(Paint.Align.CENTER);
            mPaint.setTextSize(60);
        }

        // Get the squared distance from the center of one circle to the center of the touch.
        private double distSquared(double x1, double x2, double y1, double y2) {
            return (Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
        }

        // If the distance from the radius of one circle to the radius of another is less than the
        // sum of the radii (with some lee-way), then the circles are touching.
        private boolean circlesAreTouching(int x1, int x2, int y1, int y2, float radius1, float radius2) {
            return (distSquared(x1 + radius1 / 2, x2 + radius2 / 2, y1 + radius1 / 2, y2 + radius2 / 2) <= 0.9*Math.pow(radius1 + radius2, 2));
        }

        // Random color component from a min to max.
        private int getRandColorComponent(int min, int max) {
            return mRandom.nextInt(max - min) + min;
        }

        // A circle is created randomly within the View's parameters with a random radius and color.
        private Circle newRandCircle() {
            int x = mRandom.nextInt( mScreenWidth - mRadius*2) + mRadius,
                    y = mRandom.nextInt( mScreenHeight - mRadius*2) + mRadius;
            float radius = mRadius * (mRandom.nextFloat() * (1.0f - 0.8f) + 0.8f);
            int color = Color.rgb(getRandColorComponent(0, 100), getRandColorComponent(100, 200), getRandColorComponent(190, 235));

            return new Circle(x, y, radius, color);
        }

        @Override
        protected void onSizeChanged(int newX, int newY, int oldX, int oldY){
            super.onSizeChanged(newX, newY, oldX, oldY);

            mScreenWidth = newX;
            mScreenHeight = newY;

            for( int i = 0; i < 20; i++ ) {
                mCircles[i] = newRandCircle();
            }
        }

        @Override
        public void onDraw(Canvas canvas) {
            for( int i = 0; i < 20; i++ ) {
                mPaint.setColor(mCircles[i].color);
                canvas.drawCircle(mCircles[i].x, mCircles[i].y, mCircles[i].radius, mPaint);
            }

            mPaint.setColor(Color.BLACK);
            canvas.drawText(String.format(getResources().getString(R.string.score), mScore), mScreenWidth / 2, 100, mPaint);
            canvas.drawText(mTimerText, mScreenWidth/2, 150, mPaint);
        }

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            // Check each circle to see if it was touched. If it was, create a new circle and increment score.
            if(event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                for (int i = 19; i >= 0; i--) {
                    if (circlesAreTouching( mCircles[i].x, (int)event.getX(), mCircles[i].y, (int)event.getY(), mCircles[i].radius, mCircles[i].radius )) {
                        mScore++;
                        mCircles[i] = newRandCircle();
                        break;
                    }
                }

                invalidate();
                return true;
            }

            return false;
        }
    }
}
