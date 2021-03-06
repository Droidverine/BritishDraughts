package com.droidverine.draughtsmd;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

// Green player 2 == 1
//Blue player 1 ==0
// Green king moves forward (BAck to origin) with forced moves
public class DraughtsView extends View {
    int numbRows;
    int numbColumns;
    Paint paint;
    Paint tile1;
    Paint tile2;
    Paint player1pawn;
    Paint player2pawn;
    boolean chainb;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int width;
    int numberofwhitedraughts;
    private boolean[][] cellChecked;
    private String[][] players;
    private String[][] moveable;
    private String[][] moveableplayer1;
    private boolean[][] allowedmove;
    int cellHeight;
    int cellWidth;
    int movement = 1;
    boolean selectedmoveflag;
    int highlight = 0;
    private boolean[][] highlightarr;
    private String[][] Selectedmove;
    private String[][] SelectedPlayer;
    int Selectedplayerrow, SelectedPlayercol;
    int initialize;
    int height;
    TextView txtViewpl1;
    TextView txtViewpl2;
    public DraughtsView(Context context) {
        super(context);
        init(null);

    }

    public void reset(int init) {

        initialize = init;
        Log.d("initala", "" + init);
        invalidate();

    }

    public DraughtsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public DraughtsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);

    }

    private void init(@Nullable AttributeSet attrs) {
        //Initialize players
        sharedPreferences= getContext().getSharedPreferences("draughts",0);
        SharedPreferences.Editor editor=sharedPreferences.edit();


        String player1color = sharedPreferences.getString("player1", null);
        if(player1color==null)
        {
            editor.putString("player1","#00ff00");
            editor.commit();

        }
        String player2color = sharedPreferences.getString("player2", null);
        if(player2color==null)
        {
            editor.putString("player2","#0000ff");
            editor.commit();

        }
        String tile1color = sharedPreferences.getString("tile1", null);
        if(tile1color==null)
        {
            editor.putString("tile1","#ffffff");
            editor.commit();

        }
        String tile2color = sharedPreferences.getString("tile2", null);
        if(tile2color==null)
        {
            editor.putString("tile2","#000000");
            editor.commit();


        }
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        numbColumns = 8;
        numbRows = 8;
        numberofwhitedraughts = 12;
        highlightarr = new boolean[numbRows][numbColumns];
        allowedmove = new boolean[numbRows][numbColumns];
        players = new String[numbRows][numbColumns];
        Selectedmove = new String[numbRows][numbColumns];
        SelectedPlayer = new String[numbRows][numbColumns];
        //player color set
        player1pawn = new Paint(Paint.ANTI_ALIAS_FLAG);
        player2pawn = new Paint(Paint.ANTI_ALIAS_FLAG);
        tile1=new Paint(Paint.ANTI_ALIAS_FLAG);
        tile2=new Paint(Paint.ANTI_ALIAS_FLAG);

       // player1pawn.setColor(Color.BLUE);
        if(player1color==null)
        {
            player1pawn.setColor(Color.parseColor("#0000ff"));

        }
        else {
            player1pawn.setColor(Color.parseColor(player1color));

        }
        if(player2color==null)
        {

            player2pawn.setColor(Color.parseColor("#00ff00"));
        }
        else {
            player2pawn.setColor(Color.parseColor(player2color));

        }

        //
        if(tile1color==null)
        {
            tile1.setColor(Color.parseColor("#ffffff"));

        }
        else {
            tile1.setColor(Color.parseColor(tile1color));

        }
        if(tile2color==null)
        {

            tile2.setColor(Color.parseColor("#000000"));
        }
        else {
            tile2.setColor(Color.parseColor(tile2color));

        }

//        player2pawn.setColor(Color.GREEN);

        players[0][0] = "player1";
        players[0][2] = "player1";
        players[0][4] = "player1";
        players[0][6] = "player1";
        players[1][1] = "player1";
        players[1][3] = "player1";
        players[1][5] = "player1";
        players[1][7] = "player1";
        players[2][0] = "player1";
        players[2][2] = "player1";
        players[2][4] = "player1";
        players[2][6] = "player1";

        players[5][1] = "player2";
        players[5][3] = "player2";
        players[5][5] = "player2";
        players[5][7] = "player2";
        players[6][0] = "player2";
        players[6][2] = "player2";
        players[6][4] = "player2";
        players[6][6] = "player2";
        players[7][1] = "player2";
        players[7][3] = "player2";
        players[7][5] = "player2";
        players[7][7] = "player2";
        moveable = new String[numbRows][numbColumns];
        moveableplayer1 = new String[numbRows][numbColumns];


    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Set board as per screen size
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int maxwidth = getMeasuredWidth();
        int maxheight = getMeasuredHeight();
        if (maxwidth < maxheight) {
            width = maxwidth;
            height = maxwidth;
            setMeasuredDimension(width, height);

        } else if (maxwidth > maxheight) {
            width = maxheight;
            height = maxheight;
            setMeasuredDimension(width, height);
        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);



        TextView txtViewpl1 = (TextView) ((Activity) getContext()).findViewById(R.id.player2score);
        TextView txtViewpl2 = (TextView) ((Activity) getContext()).findViewById(R.id.player1score);
        String player1color = sharedPreferences.getString("player1", null);

        String player2color = sharedPreferences.getString("player2", null);

        // player1pawn.setColor(Color.BLUE);
        if(player2color!=null)
        {
            player1pawn.setColor(Color.parseColor(player2color));
            txtViewpl1.setTextColor(Color.parseColor(player2color));


        }
        else {
            player1pawn.setColor(Color.parseColor("#0000ff"));

        }
        if(player1color!=null)
        {
            player2pawn.setColor(Color.parseColor(player1color));
            txtViewpl2.setTextColor(Color.parseColor(player1color));

        }
        else {
            player2pawn.setColor(Color.parseColor("#00ff00"));


        }

        //
        String tile1color = sharedPreferences.getString("tile1", null);

        String tile2color = sharedPreferences.getString("tile2", null);

        // player1pawn.setColor(Color.BLUE);
        if(tile1color!=null)
        {
            tile1.setColor(Color.parseColor(tile1color));


        }
        else {
            tile1.setColor(Color.parseColor("#ffffff"));

        }
        if(tile2color!=null)
        {
            tile2.setColor(Color.parseColor(tile2color));

        }
        else {
            tile2.setColor(Color.parseColor("#000000"));


        }


        TextView turntxt = (TextView) ((Activity) getContext()).findViewById(R.id.playerturn);

        if(movement == 1)
        {   if(chainb!=true)
        {
            turntxt.setText("Player 1 turn");
        }else{
            turntxt.setText("Player 1 Chain turn");

        }
            if(player1color!=null)
            {
                turntxt.setTextColor(Color.parseColor(player1color));

            }

        }else if(movement == 0)
        {   if(chainb!=true)
        {
            turntxt.setText("Player 2 turn");
        }else {
            turntxt.setText("Player 2 Chain turn");

        }
            if(player2color!=null)
            {
                turntxt.setTextColor(Color.parseColor(player2color));
            }

        }

        cellHeight = width / numbRows;
        cellWidth = height / numbColumns;
        cellChecked = new boolean[numbRows][numbColumns];

        for (int col = 0; col < numbColumns; col++) {
            for (int row = 0; row < numbRows; row++) {
                for (int whitedraugt = 0; whitedraugt < numberofwhitedraughts; whitedraugt++) {
                    Log.d("white", "" + whitedraugt);


                    if (col % 2 == 0) {
                        if (row % 2 == 0) {
                        //    paint.setColor(Color.WHITE);
                            canvas.drawRect(col * cellWidth, row *
                                    cellHeight, (col + 1) * cellWidth, (row +
                                    1) * cellHeight, tile1);


                        } else {
                          //  paint.setColor(Color.BLACK);
                            canvas.drawRect(col * cellWidth, row *
                                    cellHeight, (col + 1) * cellWidth, (row +
                                    1) * cellHeight, tile2);


                        }
                    } else {
                        if (row % 2 == 0) {
                          //  paint.setColor(Color.BLACK);
                            canvas.drawRect(col * cellWidth, row *
                                    cellHeight, (col + 1) * cellWidth, (row +
                                    1) * cellHeight, tile2);


                        } else {
                         //   paint.setColor(Color.WHITE);
                            canvas.drawRect(col * cellWidth, row *
                                    cellHeight, (col + 1) * cellWidth, (row +
                                    1) * cellHeight, tile1);

                        }
                    }
                }
            }

        }

        //player drawing
        if (initialize == 0) {
            invalidate();
             txtViewpl1 = (TextView) ((Activity) getContext()).findViewById(R.id.player2score);
            txtViewpl1.setText("Player 2\n"+ "12");
            if(player2color!=null)
            {
                txtViewpl1.setTextColor(Color.parseColor(player2color));

            }
             txtViewpl2 = (TextView) ((Activity) getContext()).findViewById(R.id.player1score);
            txtViewpl2.setText("Player 1\n" + "12");
            if(player1color!=null)
            {
                txtViewpl2.setTextColor(Color.parseColor(player1color));

            }
            canvas.translate(0, 0);

            for (int row = 0; row < numbRows; row++) {
                for (int col = 0; col < numbColumns; col++) {
                    if (players[row][col] == "player1") {


                        RectF rect = new RectF(col * cellHeight, row * cellHeight,
                                (col + 1) * cellWidth, (row +
                                1) * cellHeight);


                        canvas.drawOval(rect, player1pawn);
                    } else if (players[row][col] == "player2") {


                        RectF rect = new RectF(col * cellHeight, row * cellHeight,
                                (col + 1) * cellWidth, (row +
                                1) * cellHeight);

                        canvas.drawOval(rect, player2pawn);
                    }
                    Log.d("Players", " row " + row + " col:" + col + " " + players[row][col]);
                }

            }

            Log.d("init", "init =" + initialize);
            initialize = 1;

        } else if (initialize == 1) {
            Log.d("countinue", "init =" + initialize);
            for (int row = 0; row < numbRows; row++) {
                for (int col = 0; col < numbColumns; col++) {
                    if (players[row][col] == "player1") {


                        RectF rect = new RectF(col * cellHeight, row * cellHeight,
                                (col + 1) * cellWidth, (row +
                                1) * cellHeight);


                        canvas.drawOval(rect, player1pawn);

                    }
                    //King color Logic//

                    else if (players[row][col] == "player2king") {
                        RectF rect = new RectF(col * cellHeight, row * cellHeight,
                                (col + 1) * cellWidth, (row +
                                1) * cellHeight);


                        canvas.drawOval(rect, player2pawn);
                        // canvas.translate(col*cellHeight,row*cellHeight);
                        //Paint paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
                        paint.setColor(Color.WHITE);
                        //  Bitmap b=BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);
                        paint.setColor(Color.RED);
                        //canvas.drawBitmap(b, col*cellHeight, row*cellHeight, paint);Paint paint = new Paint();
                        //paint.setColor(Color.WHITE);
                        //paint.setStyle(Style.FILL);
                        //canvas.drawPaint(paint);
                        //
                        //paint.setColor(Color.BLACK);
                        //paint.setTextSize(20);
                        //canvas.drawText("Some Text", 10, 25, paint);
                        Paint paint = new Paint();
                        paint.setColor(Color.WHITE);
                        // paint.setStyle(Paint.Style.FILL);
                        // canvas.drawPaint(paint);

                        paint.setColor(Color.BLACK);
                        paint.setTextSize(40);
                        canvas.drawCircle(rect.centerX(), rect.centerY(), cellHeight / 4, paint);
                        //canvas.drawText("King", rect.centerX(), rect.centerY() ,paint); // center text inside rect
                        //   canvas.drawText("Parent",   col*cellHeight, (col+1)*cellWidth,paint);


                    } else if (players[row][col] == "player1king") {
                        RectF rect = new RectF(col * cellHeight, row * cellHeight,
                                (col + 1) * cellWidth, (row +
                                1) * cellHeight);

                        canvas.drawOval(rect, player1pawn);

                        paint.setColor(Color.WHITE);
                        paint.setColor(Color.RED);

                        //paint.setColor(Color.BLACK);
                        //paint.setTextSize(20);
                        //canvas.drawText("Some Text", 10, 25, paint);
                        Paint paint = new Paint();
                        paint.setColor(Color.WHITE);
                        // paint.setStyle(Paint.Style.FILL);
                        // canvas.drawPaint(paint);

                        paint.setColor(Color.BLACK);
                        paint.setTextSize(40);
                        canvas.drawCircle(rect.centerX(), rect.centerY(), cellHeight / 4, paint);
                        //canvas.drawText("King", rect.centerX(), rect.centerY() ,paint); // center text inside rect
                        //   canvas.drawText("Parent",   col*cellHeight, (col+1)*cellWidth,paint);


                    } else if (players[row][col] == "player2") {


                        RectF rect = new RectF(col * cellHeight, row * cellHeight,
                                (col + 1) * cellWidth, (row +
                                1) * cellHeight);
                        //  players[row][col] = "white";
                        paint.setColor(Color.GREEN);

                        canvas.drawOval(rect, player2pawn);
                    }
                    Log.d("Players", " row " + row + " col:" + col + " " + players[row][col]);
                }

            }

        }


        if (highlight > 0) {
            for (int row = 0; row < numbRows; row++) {
                for (int col = 0; col < numbColumns; col++) {
                    if (highlightarr[row][col]) {
                        paint.setColor(Color.rgb(235, 52, 143));
                        canvas.drawRect(col * cellWidth, row *
                                cellHeight, (col + 1) * cellWidth, (row +
                                1) * cellHeight, paint);
                        highlightarr[row][col] = false;
                        players[row][col] = null;
                        Selectedmove[row][col] = "move";
                        selectedmoveflag = true;


                    }

                    Log.d("Players", " row " + row + " col:" + col + " " + players[row][col]);
                }

            }


        }


    }
    /* NOTE THIS COMMENTS:
    //Green player 2 == 1
    //Blue player 1 ==0

     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int player1ct, player2ct;
        player1ct = 0;
        player2ct = 0;
        for (int i = 0; i < numbRows; i++) {
            for (int j = 0; j < numbColumns; j++) {
                if (players[i][j] == "player1" || players[i][j] == "player1king") {
                    player1ct++;
                } else if (players[i][j] == "player2" || players[i][j] == "player2king") {
                    player2ct++;
                }
            }
        }

         txtViewpl1 = (TextView) ((Activity) getContext()).findViewById(R.id.player2score);
        int player1color=player1pawn.getColor();
        int player2color=player2pawn.getColor();

        txtViewpl1.setText("Player 2\n"+ player1ct);
        txtViewpl1.setTextColor(player1color);
         txtViewpl2 = (TextView) ((Activity) getContext()).findViewById(R.id.player1score);
        txtViewpl2.setText("Player 1\n" + player2ct);
        txtViewpl2.setTextColor(player2color);

        Boolean comp = false;


        if (event.getAction() == MotionEvent.ACTION_DOWN) {


            int column = (int) (event.getX() / cellWidth);
            int row = (int) (event.getY() / cellHeight);

            // to check player2 & force move
            //need to set player movemable or note
            if (movement == 1) {
                for (int i = 0; i < numbRows; i++) {
                    for (int j = 0; j < numbColumns; j++) {
                        if (players[i][j] == "player2") {
                            if (i != 0 && j != 0 && (players[i - 1][j - 1] == "player1" || players[i - 1][j - 1] == "player1king") && i > 1 && j > 1 && players[i - 2][j - 2] == null) {

                                moveable[i][j] = "must";
                                comp = true;
                                Log.d("Green cmp", "Row :" + i + " Column:" + j + " status: " + players[i][j]);

                            } else if (i != 0 && j != 7 && (players[i - 1][j + 1] == "player1" || players[i - 1][j + 1] == "player1king") && i > 1 && j < 6 && players[i - 2][j + 2] == null) {

                                moveable[i][j] = "must";
                                comp = true;
                                Log.d("Green cmp", "Row :" + i + " Column:" + j + " status: " + players[i][j]);

                            }


                        } else if (players[i][j] == "player2king") {
                            //   Log.d("King cmp", "Row :" + i + " Column:" + j + " status: " + players[i][j]);

                            if (i < 6 && j > 1 && (players[i + 1][j - 1] == "player1" || players[i + 1][j - 1] == "player1king") && players[i + 2][j - 2] == null) {

                                moveable[i][j] = "must";
                                comp = true;
                                Log.d("King cmp", "Row :" + i + " Column:" + j + " status: " + players[i][j]);

                            } else if (i < 7 && j < 6 && (players[i + 1][j + 1] == "player1" || players[i + 1][j + 1] == "player1king") && players[i + 2][j + 2] == null) {

                                moveable[i][j] = "must";
                                comp = true;
                                Log.d("King cmp", "Row :" + i + " Column:" + j + " status: " + players[i][j]);

                            } else if (i != 0 && j > 1 && (players[i - 1][j - 1] == "player1" || players[i - 1][j - 1] == "player1king") && players[i - 2][j - 2] == null) {

                                moveable[i][j] = "must";
                                comp = true;
                                Log.d("King cmp", "Row :" + i + " Column:" + j + " status: " + players[i][j]);

                            } else if (i != 0 && j < 6 && (players[i - 1][j + 1] == "player1" || players[i - 1][j + 1] == "player1king") && players[i - 2][j + 2] == null) {

                                moveable[i][j] = "must";
                                comp = true;
                                Log.d("King cmp", "Row :" + i + " Column:" + j + " status: " + players[i][j]);

                            }
                        }
                    }
                }
                //New Highlight
                if (comp != true && moveable[row][column] != "must" && movement == 1) {

                    Log.d("GreenMove", "Row :" + row + " Column:" + column + " status: " + players[row][column]);

                    if (column != 7) {

///////Green Right
                        if (players[row][column] == "player2") {
                            if (players[row][column] == "player2" && row > 1 && column < 6 && (players[row - 1][column + 1] == "player1" || players[row - 1][column + 1] == "player1king") && players[row - 2][column + 2] == null) {
                                SelectedPlayer[row][column] = players[row][column];
                                Selectedplayerrow = row;
                                SelectedPlayercol = column;
                                highlightarr[row - 2][column + 2] = true;
                                //  allowedmove[row-2][column+2]=true;
                                highlight++;
                                Log.d("GreenCheck", "Blue mila " + players[row - 2][column + 2]);


                            } else {
                                if (players[row][column] == "player2" && row > 0 && players[row - 1][column + 1] == null) {
                                    SelectedPlayer[row][column] = players[row][column];
                                    Selectedplayerrow = row;
                                    SelectedPlayercol = column;
                                    highlightarr[row - 1][column + 1] = true;
                                    //  allowedmove[row-1][column+1]=true;

                                    highlight++;

                                }
                            }
                        } else if (comp != true && moveable[row][column] != "must" && players[row][column] == "player2king") {

                            if (row < 6 && column < 6 && (players[row + 1][column + 1] == "player1" || players[row + 1][column + 1] == "player1king") && players[row + 2][column + 2] == null) {
                                SelectedPlayer[row][column] = players[row][column];
                                Selectedplayerrow = row;
                                SelectedPlayercol = column;
                                highlightarr[row + 2][column + 2] = true;
                                highlight++;


                            } else {
                                if (row < 7 && players[row + 1][column + 1] == null) {
                                    SelectedPlayer[row][column] = players[row][column];
                                    Selectedplayerrow = row;
                                    SelectedPlayercol = column;
                                    highlightarr[row + 1][column + 1] = true;
                                    allowedmove[row + 1][column + 1] = true;
                                    highlight++;

                                }
                            }
                            if (row > 1 && column < 6 && (players[row - 1][column + 1] == "player1" || players[row - 1][column + 1] == "player1king") && players[row - 2][column + 2] == null) {
                                SelectedPlayer[row][column] = players[row][column];
                                Selectedplayerrow = row;
                                SelectedPlayercol = column;
                                highlightarr[row - 2][column + 2] = true;
                                highlight++;
                                Log.d("GreenCheck", "Blue mila " + players[row - 2][column + 2]);


                            } else {
                                if (row > 0 && players[row - 1][column + 1] == null) {
                                    SelectedPlayer[row][column] = players[row][column];
                                    Selectedplayerrow = row;
                                    SelectedPlayercol = column;
                                    highlightarr[row - 1][column + 1] = true;
                                    highlight++;

                                }
                            }

                        }

                    }
                    if (column != 0) {

///////Green LEFT
                        if (players[row][column] == "player2") {
                            if (row > 0 && players[row - 1][column - 1] == null) {
                                Log.d("GreenCheck", "Left check " + players[row - 1][column - 1]);
                                highlightarr[row - 1][column - 1] = true;
                                SelectedPlayer[row][column] = players[row][column];
                                Selectedplayerrow = row;
                                SelectedPlayercol = column;
                                highlight++;

                            } else if (row > 1 && column > 0 && (players[row - 1][column - 1] == "player1" || players[row - 1][column - 1] == "player1king") && column != 1 && players[row - 2][column - 2] == null) {
                                SelectedPlayer[row][column] = players[row][column];
                                Selectedplayerrow = row;
                                SelectedPlayercol = column;
                                highlightarr[row - 2][column - 2] = true;
                                highlight++;
                                Log.d("GreenCheck", "Blue mila " + players[row - 2][column - 2]);

                            }


                        } else if (comp != true && moveable[row][column] != "must" && players[row][column] == "player2king") {
                            if (row < 6 && column > 1 && players[row + 1][column - 1] == "player1" && players[row + 2][column - 2] == null) {
                                SelectedPlayer[row][column] = players[row][column];
                                Selectedplayerrow = row;
                                SelectedPlayercol = column;
                                highlightarr[row + 2][column - 2] = true;
                                highlight++;
                                Log.d("King Check", "Blue mila " + players[row + 2][column - 2]);


                            } else {
                                if (row < 7 && players[row + 1][column - 1] == null) {
                                    SelectedPlayer[row][column] = players[row][column];
                                    Selectedplayerrow = row;
                                    SelectedPlayercol = column;
                                    highlightarr[row + 1][column - 1] = true;
                                    highlight++;

                                }

                            }
                            if (row > 1 && column > 1 && (players[row - 1][column - 1] == "player1" || players[row - 1][column - 1] == "player1king") && players[row - 2][column - 2] == null) {
                                SelectedPlayer[row][column] = players[row][column];
                                Selectedplayerrow = row;
                                SelectedPlayercol = column;
                                highlightarr[row - 2][column - 2] = true;
                                highlight++;
                                Log.d("King Check", "Blue mila " + players[row - 2][column - 2]);


                            } else {
                                if (row > 0 && players[row - 1][column - 1] == null) {
                                    SelectedPlayer[row][column] = players[row][column];
                                    Selectedplayerrow = row;
                                    SelectedPlayercol = column;
                                    highlightarr[row - 1][column - 1] = true;
                                    highlight++;

                                }

                            }
                        }

                    }

                }

                if (comp == true && moveable[row][column] == "must") {
                    if (players[row][column] == "player2") {
                        if (column != 7) {

///////Green Right

                            if (row > 1 && column < 6 && (players[row - 1][column + 1] == "player1" || players[row - 1][column + 1] == "player1king") && players[row - 2][column + 2] == null) {
                                SelectedPlayer[row][column] = players[row][column];
                                Selectedplayerrow = row;
                                SelectedPlayercol = column;
                                highlightarr[row - 2][column + 2] = true;
                                highlight++;
                                Log.d("GreenCheck", "Blue mila " + players[row - 2][column + 2]);


                            }

                        }
                        if (column != 0) {

///////Green LEFT

                            if (row > 1 && column > 0 && (players[row - 1][column - 1] == "player1" || players[row - 1][column - 1] == "player1king") && column != 1 && players[row - 2][column - 2] == null) {
                                SelectedPlayer[row][column] = players[row][column];
                                Selectedplayerrow = row;
                                SelectedPlayercol = column;
                                highlightarr[row - 2][column - 2] = true;
                                highlight++;
                                Log.d("GreenCheck", "Blue mila " + players[row - 2][column - 2]);

                            }


                        }


                    } else if (comp == true && moveable[row][column] == "must" && players[row][column] == "player2king") {
                        if (column != 7) {

///////Green Right

                            if (row < 7 && column < 6 && (players[row + 1][column + 1] == "player1" || players[row + 1][column + 1] == "player1king") && players[row + 2][column + 2] == null) {
                                SelectedPlayer[row][column] = players[row][column];
                                Selectedplayerrow = row;
                                SelectedPlayercol = column;
                                highlightarr[row + 2][column + 2] = true;
                                highlight++;
                                //  Log.d("GreenCheck", "Blue mila " + players[row - 2][column + 2]);


                            }  if (row > 1 && column < 6 && (players[row - 1][column + 1] == "player1" || players[row - 1][column + 1] == "player1king") && players[row - 2][column + 2] == null) {
                                SelectedPlayer[row][column] = players[row][column];
                                Selectedplayerrow = row;
                                SelectedPlayercol = column;
                                highlightarr[row - 2][column + 2] = true;
                                highlight++;
                                //  Log.d("GreenCheck", "Blue mila " + players[row - 2][column + 2]);


                            }

                        }
                        if (column != 0) {

///////Green LEFT

                            if (row < 7 && column > 0 && (players[row + 1][column - 1] == "player1" || players[row + 1][column - 1] == "player1king") && column != 1 && players[row + 2][column - 2] == null) {
                                SelectedPlayer[row][column] = players[row][column];
                                Selectedplayerrow = row;
                                SelectedPlayercol = column;
                                highlightarr[row + 2][column - 2] = true;
                                highlight++;
                                //     Log.d("GreenCheck", "Blue mila " + players[row - 2][column - 2]);

                            }  if (row > 1 && column > 0 && (players[row - 1][column - 1] == "player1" || players[row - 1][column - 1] == "player1king") && column != 1 && players[row - 2][column - 2] == null) {
                                SelectedPlayer[row][column] = players[row][column];
                                Selectedplayerrow = row;
                                SelectedPlayercol = column;
                                highlightarr[row - 2][column - 2] = true;
                                highlight++;
                                //     Log.d("GreenCheck", "Blue mila " + players[row - 2][column - 2]);

                            }


                        }


                    }

                }
                for (int i = 0; i < numbRows; i++) {
                    for (int j = 0; j < numbColumns; j++) {
                        moveable[row][column] = null;

                    }
                }
                comp = false;

            }

// To check Player1 & force move
            //need to set player movemable or note
            else if (movement == 0) {
                Log.d("BLue bhetla", "player1");

                for (int i = 0; i < numbRows; i++) {
                    for (int j = 0; j < numbColumns; j++) {
                        if (players[i][j] == "player1") {
                            if (i != 7 && j != 0 && (players[i + 1][j - 1] == "player2" || players[i + 1][j - 1] == "player2king") && i < 6 && j > 1 && players[i + 2][j - 2] == null) {

                                moveable[i][j] = "must";
                                comp = true;
                                Log.d("Blue cmp", "Row :" + i + " Column:" + j + " status: " + players[i][j]);

                            } else if (i != 7 && j != 7 && (players[i + 1][j + 1] == "player2" || players[i + 1][j + 1] == "player2king") && i < 6 && j < 6 && players[i + 2][j + 2] == null) {

                                moveable[i][j] = "must";
                                comp = true;
                                Log.d("Blue cmp", "Row :" + i + " Column:" + j + " status: " + players[i][j]);

                            }


                        } else if (players[i][j] == "player1king") {
                            //   Log.d("King cmp", "Row :" + i + " Column:" + j + " status: " + players[i][j]);

                            if (i < 6 && j > 1 && (players[i + 1][j - 1] == "player2" || players[i + 1][j - 1] == "player2king") && players[i + 2][j - 2] == null) {

                                moveable[i][j] = "must";
                                comp = true;
                                Log.d("King cmp", "Row :" + i + " Column:" + j + " status: " + players[i][j]);

                            } else if (i < 7 && j < 6 && (players[i + 1][j + 1] == "player2" || players[i + 1][j + 1] == "player2king") && players[i + 2][j + 2] == null) {

                                moveable[i][j] = "must";
                                comp = true;
                                Log.d("King cmp", "Row :" + i + " Column:" + j + " status: " + players[i][j]);

                            } else if (i > 1 && j > 1 && (players[i - 1][j - 1] == "player2" || players[i - 1][j - 1] == "player2king") && players[i - 2][j - 2] == null) {

                                moveable[i][j] = "must";
                                comp = true;
                                Log.d("King cmp", "Row :" + i + " Column:" + j + " status: " + players[i][j]);

                            } else if (i > 1 && j < 6 && (players[i - 1][j + 1] == "player2" || players[i - 1][j + 1] == "player2king") && players[i - 2][j + 2] == null) {

                                moveable[i][j] = "must";
                                comp = true;
                                Log.d("King cmp", "Row :" + i + " Column:" + j + " status: " + players[i][j]);

                            }
                        }
                    }
                }
                //New Highlight
                if (comp != true && moveable[row][column] != "must" && movement == 0) {
                    Log.d("BlueMove", "Row :" + row + " Column:" + column + " status: " + players[row][column]);

                    if (column != 7) {

///////Blue Right
                        if (players[row][column] == "player1") {
                            if (players[row][column] == "player1" && row < 7 && column < 6 && (players[row + 1][column + 1] == "player2" || players[row + 1][column + 1] == "player2king") && players[row + 2][column + 2] == null) {
                                SelectedPlayer[row][column] = players[row][column];
                                Selectedplayerrow = row;
                                SelectedPlayercol = column;
                                highlightarr[row + 2][column + 2] = true;
                                highlight++;
                                Log.d("GreenCheck", "Blue mila " + players[row + 2][column + 2]);


                            } else {
                                if (players[row][column] == "player1" && row < 7 && players[row + 1][column + 1] == null) {
                                    SelectedPlayer[row][column] = players[row][column];
                                    Selectedplayerrow = row;
                                    SelectedPlayercol = column;
                                    highlightarr[row + 1][column + 1] = true;
                                    highlight++;

                                }
                            }
                        } else if (comp != true && moveableplayer1[row][column] != "must" && players[row][column] == "player1king") {

                            if (row < 6 && column < 6 && (players[row + 1][column + 1] == "player2" || players[row + 1][column + 1] == "player2king") && players[row + 2][column + 2] == null) {
                                SelectedPlayer[row][column] = players[row][column];
                                Selectedplayerrow = row;
                                SelectedPlayercol = column;
                                highlightarr[row + 2][column + 2] = true;
                                highlight++;
                                Log.d("King Check", "Blue mila " + players[row + 2][column + 2]);


                            } else {
                                if (row < 7 && players[row + 1][column + 1] == null) {
                                    SelectedPlayer[row][column] = players[row][column];
                                    Selectedplayerrow = row;
                                    SelectedPlayercol = column;
                                    highlightarr[row + 1][column + 1] = true;
                                    allowedmove[row + 1][column + 1] = true;
                                    highlight++;

                                }
                            }
                            if (row > 1 && column < 6 && (players[row - 1][column + 1] == "player2" || players[row - 1][column + 1] == "player2king") && players[row - 2][column + 2] == null) {
                                SelectedPlayer[row][column] = players[row][column];
                                Selectedplayerrow = row;
                                SelectedPlayercol = column;
                                highlightarr[row - 2][column + 2] = true;
                                highlight++;
                                Log.d("Bluecheck", "green mila " + players[row - 2][column + 2]);


                            } else {
                                if (row > 0 && players[row - 1][column + 1] == null) {
                                    SelectedPlayer[row][column] = players[row][column];
                                    Selectedplayerrow = row;
                                    SelectedPlayercol = column;
                                    highlightarr[row - 1][column + 1] = true;
                                    highlight++;

                                }
                            }

                        }

                    }
                    if (column != 0) {

///////Blue LEFT
                        if (players[row][column] == "player1") {
                            if (row < 7 && players[row + 1][column - 1] == null) {
                                Log.d("BlueCheck", "Left check " + players[row + 1][column - 1]);
                                highlightarr[row + 1][column - 1] = true;
                                SelectedPlayer[row][column] = players[row][column];
                                Selectedplayerrow = row;
                                SelectedPlayercol = column;
                                highlight++;

                            } else if (row < 6 && column > 0 && (players[row + 1][column - 1] == "player2" || players[row + 1][column - 1] == "player2king") && column != 1 && players[row + 2][column - 2] == null) {
                                SelectedPlayer[row][column] = players[row][column];
                                Selectedplayerrow = row;
                                SelectedPlayercol = column;
                                highlightarr[row + 2][column - 2] = true;
                                highlight++;
                                Log.d("BlueCheck", "Blue mila " + players[row + 2][column - 2]);

                            }


                        } else if (comp != true && moveable[row][column] != "must" && players[row][column] == "player1king") {
                            if (row > 1 && column > 1 && (players[row - 1][column - 1] == "player2" || players[row - 1][column - 1] == "player2king") && players[row - 2][column - 2] == null) {
                                SelectedPlayer[row][column] = players[row][column];
                                Selectedplayerrow = row;
                                SelectedPlayercol = column;
                                highlightarr[row - 2][column - 2] = true;
                                highlight++;
                                // Log.d("King Check", "Blue mila " + players[row + 2][column - 2]);


                            } else {
                                if (row > 0 && column > 0 && players[row - 1][column - 1] == null) {
                                    SelectedPlayer[row][column] = players[row][column];
                                    Selectedplayerrow = row;
                                    SelectedPlayercol = column;
                                    highlightarr[row - 1][column - 1] = true;
                                    highlight++;

                                }

                            }
                            if (row > 1 && column > 1 && (players[row - 1][column - 1] == "player2" || players[row - 1][column - 1] == "player2king") && players[row - 2][column - 2] == null) {
                                SelectedPlayer[row][column] = players[row][column];
                                Selectedplayerrow = row;
                                SelectedPlayercol = column;
                                highlightarr[row - 2][column - 2] = true;
                                highlight++;
                                // Log.d("King Check", "Blue mila " + players[row - 2][column - 2]);


                            } else {
                                if (row < 7 && players[row + 1][column - 1] == null) {
                                    SelectedPlayer[row][column] = players[row][column];
                                    Selectedplayerrow = row;
                                    SelectedPlayercol = column;
                                    highlightarr[row + 1][column - 1] = true;
                                    highlight++;

                                }

                            }
                        }

                    }


                }
                if (comp == true && moveable[row][column] == "must") {
                    if (players[row][column] == "player1") {
                        if (column != 7) {

///////Green Right

                            if (row < 6 && column < 6 && (players[row + 1][column + 1] == "player2" || players[row + 1][column + 1] == "player2king") && players[row + 2][column + 2] == null) {
                                SelectedPlayer[row][column] = players[row][column];
                                Selectedplayerrow = row;
                                SelectedPlayercol = column;
                                highlightarr[row + 2][column + 2] = true;
                                highlight++;
                                //Log.d("GreenCheck", "Blue mila " + players[row - 2][column + 2]);


                            }

                        }
                        if (column != 0) {

///////Green LEFT

                            if (row < 6 && column > 0 && (players[row + 1][column - 1] == "player2" || players[row + 1][column - 1] == "player2king") && column != 1 && players[row + 2][column - 2] == null) {
                                SelectedPlayer[row][column] = players[row][column];
                                Selectedplayerrow = row;
                                SelectedPlayercol = column;
                                highlightarr[row + 2][column - 2] = true;
                                highlight++;
                                //  Log.d("GreenCheck", "Blue mila " + players[row - 2][column - 2]);

                            }


                        }


                    } else if (comp == true && moveable[row][column] == "must" && players[row][column] == "player1king") {
                        if (column != 7) {

///////Blue Right

                            if (row < 7 && column < 6 && (players[row + 1][column + 1] == "player2" || players[row + 1][column + 1] == "player2king") && players[row + 2][column + 2] == null) {
                                SelectedPlayer[row][column] = players[row][column];
                                Selectedplayerrow = row;
                                SelectedPlayercol = column;
                                highlightarr[row + 2][column + 2] = true;
                                highlight++;
                                //  Log.d("GreenCheck", "Blue mila " + players[row - 2][column + 2]);


                            }  if (row > 1 && column < 6 && (players[row - 1][column + 1] == "player2" || players[row - 1][column + 1] == "player2king") && players[row - 2][column + 2] == null) {
                                SelectedPlayer[row][column] = players[row][column];
                                Selectedplayerrow = row;
                                SelectedPlayercol = column;
                                highlightarr[row - 2][column + 2] = true;
                                highlight++;
                                //  Log.d("GreenCheck", "Blue mila " + players[row - 2][column + 2]);


                            }

                        }
                        if (column != 0) {

///////Blue LEFT

                            if (row < 7 && column > 0 && (players[row + 1][column - 1] == "player2" || players[row + 1][column - 1] == "player2king") && column != 1 && players[row + 2][column - 2] == null) {
                                SelectedPlayer[row][column] = players[row][column];
                                Selectedplayerrow = row;
                                SelectedPlayercol = column;
                                highlightarr[row + 2][column - 2] = true;
                                highlight++;
                                //     Log.d("GreenCheck", "Blue mila " + players[row - 2][column - 2]);

                            }  if (row > 1 && column > 0 && (players[row - 1][column - 1] == "player2" || players[row - 1][column - 1] == "player2king") && column != 1 && players[row - 2][column - 2] == null) {
                                SelectedPlayer[row][column] = players[row][column];
                                Selectedplayerrow = row;
                                SelectedPlayercol = column;
                                highlightarr[row - 2][column - 2] = true;
                                highlight++;
                                //     Log.d("GreenCheck", "Blue mila " + players[row - 2][column - 2]);

                            }


                        }


                    }

                }
                //must blue
                for (int i = 0; i < numbRows; i++) {
                    for (int j = 0; j < numbColumns; j++) {
                        moveable[row][column] = null;

                    }
                }
                comp = false;
            }
            Log.d("gubugubu", "" + movement);


            //For movement

            if (selectedmoveflag) {

                //Player2  Movement
                if (movement == 1 && Selectedmove[row][column] == "move") {

                    if (players[Selectedplayerrow][SelectedPlayercol] == "player2") {
                        //Green Left movement
                        if (row == Selectedplayerrow - 1 && column == SelectedPlayercol - 1) {
                            if (players[Selectedplayerrow - 1][SelectedPlayercol - 1] == null) {
                                players[Selectedplayerrow][SelectedPlayercol] = null;
                                players[Selectedplayerrow - 1][SelectedPlayercol - 1] = "player2";
                                Log.d("SelectedMOve", "Row :" + row + " Column:" + column + " status: " + SelectedPlayer[Selectedplayerrow][SelectedPlayercol]);
                                selectedmoveflag = false;
                                chainb=false;

                                movement = 0;
                            }

                            selectedmoveflag = false;


                        } else if (row == Selectedplayerrow - 2 && column == SelectedPlayercol - 2) {
                            if ((players[Selectedplayerrow - 1][SelectedPlayercol - 1] == "player1" || players[Selectedplayerrow - 1][SelectedPlayercol - 1] == "player1king") && players[Selectedplayerrow - 2][SelectedPlayercol - 2] == null) {
                                players[Selectedplayerrow][SelectedPlayercol] = null;
                                players[Selectedplayerrow - 2][SelectedPlayercol - 2] = "player2";
                                players[Selectedplayerrow - 1][SelectedPlayercol - 1] = null;

                                ///Chaining Working here/////////
                                row = Selectedplayerrow - 2;
                                column = SelectedPlayercol - 2;
                                if (row > 1 && column > 1 && (players[row - 1][column - 1] == "player1" || players[row - 1][column - 1] == "player1king") && players[row - 2][column - 2] == null) {
                                    movement = 1;
                                    selectedmoveflag = false;
                                    chainb=true;


                                } else if (row > 1 && column < 6 && (players[row - 1][column + 1] == "player1" || players[row - 1][column + 1] == "player1king") && players[row - 2][column + 2] == null) {
                                    movement = 1;
                                    selectedmoveflag = false;
                                    chainb=true;


                                } else {
                                    movement = 0;
                                    selectedmoveflag = false;
                                    chainb=false;

                                }


                            } else {
                                movement = 1;

                                selectedmoveflag = false;
                                //chainb=false;

                            }

                        } else if (row == Selectedplayerrow - 1 && column == SelectedPlayercol + 1) {
                            if (players[Selectedplayerrow - 1][SelectedPlayercol + 1] == null) {
                                players[Selectedplayerrow][SelectedPlayercol] = null;
                                players[Selectedplayerrow - 1][SelectedPlayercol + 1] = "player2";

                                Log.d("SelectedMOve", "Row :" + row + " Column:" + column + " status: " + SelectedPlayer[Selectedplayerrow][SelectedPlayercol]);
                                selectedmoveflag = false;
                                movement = 0;
                                chainb=false;

                            } else {
                                selectedmoveflag = false;

                            }

                        } else if (row == Selectedplayerrow - 2 && column == SelectedPlayercol + 2) {
                            if ((players[Selectedplayerrow - 1][SelectedPlayercol + 1] == "player1" || players[Selectedplayerrow - 1][SelectedPlayercol + 1] == "player1king") && players[Selectedplayerrow - 2][SelectedPlayercol + 2] == null) {
                                players[Selectedplayerrow][SelectedPlayercol] = null;
                                players[Selectedplayerrow - 2][SelectedPlayercol + 2] = "player2";
                                players[Selectedplayerrow - 1][SelectedPlayercol + 1] = null;

                                row = Selectedplayerrow - 2;
                                column = SelectedPlayercol + 2;
                                if (row > 1 && column < 6 && (players[row - 1][column + 1] == "player1" || players[row - 1][column + 1] == "player1king") && players[row - 2][column + 2] == null) {
                                    movement = 1;
                                    selectedmoveflag = false;
                                    chainb=true;


                                } else if (row > 1 && column > 1 && (players[row - 1][column - 1] == "player1" || players[row - 1][column - 1] == "player1king") && players[row - 2][column - 2] == null) {
                                    movement = 1;
                                    selectedmoveflag = false;
                                    chainb=true;


                                } else {

                                    movement = 0;
                                    selectedmoveflag = false;
                                    chainb=false;


                                }


                                Log.d("SelectedMOve", "Row :" + row + " Column:" + column + " status: " + SelectedPlayer[Selectedplayerrow][SelectedPlayercol]);

                            }

                        } else {

                            movement = 1;

                            selectedmoveflag = false;

                        }
                        if (row == 0) {
                            players[row][column] = "player2king";
                            //movement = 0;

                        }

                    }
                    //Green king movement

                    else if (players[Selectedplayerrow][SelectedPlayercol] == "player2king") {
                        if (row == Selectedplayerrow + 1 && column == SelectedPlayercol - 1) {
                            if (players[Selectedplayerrow + 1][SelectedPlayercol - 1] == null) {
                                players[Selectedplayerrow][SelectedPlayercol] = null;
                                players[Selectedplayerrow + 1][SelectedPlayercol - 1] = "player2king";
                                Log.d("SelectedMOve", "Row :" + row + " Column:" + column + " status: " + SelectedPlayer[Selectedplayerrow][SelectedPlayercol]);
                                selectedmoveflag = false;
                                chainb=false;

                                movement = 0;
                            }

                            selectedmoveflag = false;


                        } else if (row == Selectedplayerrow - 1 && column == SelectedPlayercol - 1) {
                            if (players[Selectedplayerrow - 1][SelectedPlayercol - 1] == null) {
                                players[Selectedplayerrow][SelectedPlayercol] = null;
                                players[Selectedplayerrow - 1][SelectedPlayercol - 1] = "player2king";
                                Log.d("SelectedMOve", "Row :" + row + " Column:" + column + " status: " + SelectedPlayer[Selectedplayerrow][SelectedPlayercol]);
                                selectedmoveflag = false;
                                chainb=false;

                                movement = 0;
                            }

                            selectedmoveflag = false;


                        } else if (row == Selectedplayerrow + 2 && column == SelectedPlayercol - 2) {
                            if ((players[Selectedplayerrow + 1][SelectedPlayercol - 1] == "player1" || players[Selectedplayerrow + 1][SelectedPlayercol - 1] == "player1king") && players[Selectedplayerrow + 2][SelectedPlayercol - 2] == null) {
                                players[Selectedplayerrow][SelectedPlayercol] = null;
                                players[Selectedplayerrow + 2][SelectedPlayercol - 2] = "player2king";
                                players[Selectedplayerrow + 1][SelectedPlayercol - 1] = null;

                                ///Chaining Working here/////////
                                row = Selectedplayerrow + 2;
                                column = SelectedPlayercol - 2;
                                if (row < 7 && column > 1 && (players[row + 1][column - 1] == "player1" || players[row + 1][column - 1] == "player1king") && players[row + 2][column - 2] == null) {
                                    movement = 1;
                                    selectedmoveflag = false;
                                    chainb=true;


                                } else if (row < 7 && column < 6 && (players[row + 1][column + 1] == "player1" || players[row + 1][column + 1] == "player1king") && players[row + 2][column + 2] == null) {
                                    movement = 1;
                                    selectedmoveflag = false;
                                    chainb=true;


                                } else {
                                    movement = 0;
                                    selectedmoveflag = false;
                                    chainb=false;


                                }


                            } else {
                                movement = 1;

                                selectedmoveflag = false;

                            }

                        }
                        //backward left green king capture
                        else if (row == Selectedplayerrow - 2 && column == SelectedPlayercol - 2) {
                            if ((players[Selectedplayerrow - 1][SelectedPlayercol - 1] == "player1" || players[Selectedplayerrow - 1][SelectedPlayercol - 1] == "player1king") && players[Selectedplayerrow - 2][SelectedPlayercol - 2] == null) {
                                players[Selectedplayerrow][SelectedPlayercol] = null;
                                players[Selectedplayerrow - 2][SelectedPlayercol - 2] = "player2king";
                                players[Selectedplayerrow - 1][SelectedPlayercol - 1] = null;

                                ///Chaining Working here/////////
                                row = Selectedplayerrow - 2;
                                column = SelectedPlayercol - 2;
                                if (row > 1 && column > 1 && (players[row - 1][column - 1] == "player1" || players[row - 1][column - 1] == "player1king") && players[row - 2][column - 2] == null) {
                                    movement = 1;
                                    selectedmoveflag = false;
                                    chainb=true;

                                } else if (row > 1 && column < 6 && (players[row - 1][column + 1] == "player1" || players[row - 1][column + 1] == "player1king") && players[row - 2][column + 2] == null) {
                                    movement = 1;
                                    selectedmoveflag = false;
                                    chainb=true;


                                } else {
                                    movement = 0;
                                    selectedmoveflag = false;
                                    chainb=false;


                                }


                            } else {
                                movement = 1;

                                selectedmoveflag = false;

                            }

                        } else if (row == Selectedplayerrow + 1 && column == SelectedPlayercol + 1) {
                            if (players[Selectedplayerrow + 1][SelectedPlayercol + 1] == null) {
                                players[Selectedplayerrow][SelectedPlayercol] = null;
                                players[Selectedplayerrow + 1][SelectedPlayercol + 1] = "player2king";

                                Log.d("SelectedMOve", "Row :" + row + " Column:" + column + " status: " + SelectedPlayer[Selectedplayerrow][SelectedPlayercol]);
                                selectedmoveflag = false;
                                movement = 0;
                                chainb=false;


                            } else {
                                selectedmoveflag = false;

                            }

                        } else if (row == Selectedplayerrow - 1 && column == SelectedPlayercol + 1) {
                            if (players[Selectedplayerrow - 1][SelectedPlayercol + 1] == null) {
                                players[Selectedplayerrow][SelectedPlayercol] = null;
                                players[Selectedplayerrow - 1][SelectedPlayercol + 1] = "player2king";

                                Log.d("SelectedMOve", "Row :" + row + " Column:" + column + " status: " + SelectedPlayer[Selectedplayerrow][SelectedPlayercol]);
                                selectedmoveflag = false;
                                movement = 0;
                                chainb=false;


                            } else {
                                selectedmoveflag = false;

                            }

                        }


                        //forward right king capture
                        else if (row == Selectedplayerrow + 2 && column == SelectedPlayercol + 2) {
                            if ((players[Selectedplayerrow + 1][SelectedPlayercol + 1] == "player1" || players[Selectedplayerrow + 1][SelectedPlayercol + 1] == "player1king") && players[Selectedplayerrow + 2][SelectedPlayercol + 2] == null) {
                                players[Selectedplayerrow][SelectedPlayercol] = null;
                                players[Selectedplayerrow + 2][SelectedPlayercol + 2] = "player2king";
                                players[Selectedplayerrow + 1][SelectedPlayercol + 1] = null;

                                row = Selectedplayerrow + 2;
                                column = SelectedPlayercol + 2;
                                if (row < 6 && column < 6 && (players[row + 1][column + 1] == "player1" || players[row + 1][column + 1] == "player1king") && players[row + 2][column + 2] == null) {
                                    movement = 1;
                                    selectedmoveflag = false;
                                    chainb=true;


                                } else if (row < 6 && column > 1 && (players[row + 1][column - 1] == "player1" || players[row + 1][column - 1] == "player1king") && players[row + 2][column - 2] == null) {
                                    movement = 1;
                                    selectedmoveflag = false;
                                    chainb=true;


                                } else {

                                    movement = 0;
                                    selectedmoveflag = false;
                                    chainb=false;


                                }


                                Log.d("SelectedMOve", "Row :" + row + " Column:" + column + " status: " + SelectedPlayer[Selectedplayerrow][SelectedPlayercol]);

                            }

                        }
                        //reverse right king checkmate
                        else if (row == Selectedplayerrow - 2 && column == SelectedPlayercol + 2) {
                            if ((players[Selectedplayerrow - 1][SelectedPlayercol + 1] == "player1" || players[Selectedplayerrow - 1][SelectedPlayercol + 1] == "player1king") && players[Selectedplayerrow - 2][SelectedPlayercol + 2] == null) {
                                players[Selectedplayerrow][SelectedPlayercol] = null;
                                players[Selectedplayerrow - 2][SelectedPlayercol + 2] = "player2king";
                                players[Selectedplayerrow - 1][SelectedPlayercol + 1] = null;

                                row = Selectedplayerrow - 2;
                                column = SelectedPlayercol + 2;
                                if (row > 1 && column < 6 && (players[row - 1][column + 1] == "player1" || players[row - 1][column + 1] == "player1king") && players[row - 2][column + 2] == null) {
                                    movement = 1;
                                    selectedmoveflag = false;
                                    chainb=true;

                                } else if (row > 1 && column > 1 && (players[row - 1][column - 1] == "player1" || players[row - 1][column - 1] == "player1king") && players[row - 2][column - 2] == null) {
                                    movement = 1;
                                    selectedmoveflag = false;
                                    chainb=true;

                                } else {

                                    movement = 0;
                                    selectedmoveflag = false;
                                    chainb=false;


                                }


                                Log.d("SelectedMOve", "Row :" + row + " Column:" + column + " status: " + SelectedPlayer[Selectedplayerrow][SelectedPlayercol]);

                            }

                        } else {

                            movement = 1;

                            selectedmoveflag = false;

                        }


                    }

                }

            }
            //Player1 movement
            if (movement == 0 && Selectedmove[row][column] == "move") {

                if (players[Selectedplayerrow][SelectedPlayercol] == "player1") {
                    //Green Left movement
                    if (row == Selectedplayerrow + 1 && column == SelectedPlayercol - 1) {
                        if (players[Selectedplayerrow + 1][SelectedPlayercol - 1] == null) {
                            players[Selectedplayerrow][SelectedPlayercol] = null;
                            players[Selectedplayerrow + 1][SelectedPlayercol - 1] = "player1";
                            //    Log.d("SelectedMOve", "Row :" + row + " Column:" + column + " status: " + SelectedPlayer[Selectedplayerrow][SelectedPlayercol]);
                            selectedmoveflag = false;
                            chainb=false;

                            movement = 1;
                        }

                        selectedmoveflag = false;


                    } else if (row == Selectedplayerrow + 2 && column == SelectedPlayercol - 2) {
                        if ((players[Selectedplayerrow + 1][SelectedPlayercol - 1] == "player2" || players[Selectedplayerrow + 1][SelectedPlayercol - 1] == "player2king") && players[Selectedplayerrow + 2][SelectedPlayercol - 2] == null) {
                            players[Selectedplayerrow][SelectedPlayercol] = null;
                            players[Selectedplayerrow + 2][SelectedPlayercol - 2] = "player1";
                            players[Selectedplayerrow + 1][SelectedPlayercol - 1] = null;

                            ///Chaining Working here/////////
                            row = Selectedplayerrow + 2;
                            column = SelectedPlayercol - 2;
                            if (row < 6 && column > 1 && (players[row + 1][column - 1] == "player2" || players[row + 1][column - 1] == "player2king") && players[row + 2][column - 2] == null) {
                                movement = 0;
                                selectedmoveflag = false;
                                chainb=true;

                            } else if (row < 6 && column < 6 && (players[row + 1][column + 1] == "player2" || players[row + 1][column + 1] == "player2king") && players[row + 2][column + 2] == null) {
                                movement = 0;
                                selectedmoveflag = false;
                                chainb=true;


                            } else {
                                movement = 1;
                                selectedmoveflag = false;
                                chainb=false;

                            }


                        } else {
                            movement = 0;
                            chainb=false;

                            selectedmoveflag = false;

                        }

                    } else if (row == Selectedplayerrow + 1 && column == SelectedPlayercol + 1) {
                        if (players[Selectedplayerrow + 1][SelectedPlayercol + 1] == null) {
                            players[Selectedplayerrow][SelectedPlayercol] = null;
                            players[Selectedplayerrow + 1][SelectedPlayercol + 1] = "player1";

                            // Log.d("SelectedMOve", "Row :" + row + " Column:" + column + " status: " + SelectedPlayer[Selectedplayerrow][SelectedPlayercol]);
                            selectedmoveflag = false;
                            movement = 1;
                            chainb=false;


                        } else {
                            selectedmoveflag = false;

                        }

                    } else if (row == Selectedplayerrow + 2 && column == SelectedPlayercol + 2) {
                        if ((players[Selectedplayerrow + 1][SelectedPlayercol + 1] == "player2" || players[Selectedplayerrow + 1][SelectedPlayercol + 1] == "player2king") && players[Selectedplayerrow + 2][SelectedPlayercol + 2] == null) {
                            players[Selectedplayerrow][SelectedPlayercol] = null;
                            players[Selectedplayerrow + 2][SelectedPlayercol + 2] = "player1";
                            players[Selectedplayerrow + 1][SelectedPlayercol + 1] = null;

                            row = Selectedplayerrow + 2;
                            column = SelectedPlayercol + 2;
                            if (row < 6 && column < 6 && (players[row + 1][column + 1] == "player2" || players[row + 1][column + 1] == "player2king") && players[row + 2][column + 2] == null) {
                                movement = 0;
                                selectedmoveflag = false;
                                chainb=true;


                            } else if (row < 6 && column > 1 && (players[row + 1][column - 1] == "player2" || players[row + 1][column - 1] == "player2king") && players[row + 2][column - 2] == null) {
                                movement = 0;
                                selectedmoveflag = false;
                                chainb=true;


                            } else {

                                movement = 1;
                                selectedmoveflag = false;
                                chainb=false;

                            }


                            Log.d("SelectedMOve", "Row :" + row + " Column:" + column + " status: " + SelectedPlayer[Selectedplayerrow][SelectedPlayercol]);

                        }

                    } else {

                        movement = 0;

                        selectedmoveflag = false;

                    }
                    if (row == 7) {
                        players[row][column] = "player1king";
                        //movement = 0;

                    }

                }
                //Blue king movement

                else if (players[Selectedplayerrow][SelectedPlayercol] == "player1king") {
                    if (row == Selectedplayerrow + 1 && column == SelectedPlayercol - 1) {
                        if (players[Selectedplayerrow + 1][SelectedPlayercol - 1] == null) {
                            players[Selectedplayerrow][SelectedPlayercol] = null;
                            players[Selectedplayerrow + 1][SelectedPlayercol - 1] = "player1king";
                            Log.d("SelectedMOve", "Row :" + row + " Column:" + column + " status: " + SelectedPlayer[Selectedplayerrow][SelectedPlayercol]);
                            selectedmoveflag = false;
                            chainb=false;

                            movement = 1;
                        }

                        selectedmoveflag = false;


                    } else if (row == Selectedplayerrow - 1 && column == SelectedPlayercol - 1) {
                        if (players[Selectedplayerrow - 1][SelectedPlayercol - 1] == null) {
                            players[Selectedplayerrow][SelectedPlayercol] = null;
                            players[Selectedplayerrow - 1][SelectedPlayercol - 1] = "player1king";
                            Log.d("SelectedMOve", "Row :" + row + " Column:" + column + " status: " + SelectedPlayer[Selectedplayerrow][SelectedPlayercol]);
                            selectedmoveflag = false;
                            chainb=false;

                            movement = 1;
                        }

                        selectedmoveflag = false;


                    } else if (row == Selectedplayerrow + 2 && column == SelectedPlayercol - 2) {
                        if ((players[Selectedplayerrow + 1][SelectedPlayercol - 1] == "player2" || players[Selectedplayerrow + 1][SelectedPlayercol - 1] == "player2king") && players[Selectedplayerrow + 2][SelectedPlayercol - 2] == null) {
                            players[Selectedplayerrow][SelectedPlayercol] = null;
                            players[Selectedplayerrow + 2][SelectedPlayercol - 2] = "player1king";
                            players[Selectedplayerrow + 1][SelectedPlayercol - 1] = null;

                            ///Chaining Working here/////////
                            row = Selectedplayerrow + 2;
                            column = SelectedPlayercol - 2;
                            if (row < 7 && column > 1 && (players[row + 1][column - 1] == "player2" || players[row + 1][column - 1] == "player2king") && players[row + 2][column - 2] == null) {
                                movement = 0;
                                selectedmoveflag = false;
                                chainb=true;


                            } else if (row < 7 && column < 6 && (players[row + 1][column + 1] == "player2" || players[row + 1][column + 1] == "player2king") && players[row + 2][column + 2] == null) {
                                movement = 0;
                                selectedmoveflag = false;
                                chainb=true;


                            } else {
                                movement = 1;
                                selectedmoveflag = false;
                                chainb=false;


                            }


                        } else {
                            movement = 0;

                            selectedmoveflag = false;

                        }

                    }
                    //backward left green king capture
                    else if (row == Selectedplayerrow - 2 && column == SelectedPlayercol - 2) {
                        if ((players[Selectedplayerrow - 1][SelectedPlayercol - 1] == "player2" || players[Selectedplayerrow - 1][SelectedPlayercol - 1] == "player2king") && players[Selectedplayerrow - 2][SelectedPlayercol - 2] == null) {
                            players[Selectedplayerrow][SelectedPlayercol] = null;
                            players[Selectedplayerrow - 2][SelectedPlayercol - 2] = "player1king";
                            players[Selectedplayerrow - 1][SelectedPlayercol - 1] = null;

                            ///Chaining Working here/////////
                            row = Selectedplayerrow - 2;
                            column = SelectedPlayercol - 2;
                            if (row > 1 && column > 1 && (players[row - 1][column - 1] == "player2" || players[row - 1][column - 1] == "player2king") && players[row - 2][column - 2] == null) {
                                movement = 0;
                                selectedmoveflag = false;
                                chainb=true;


                            } else if (row > 1 && column < 6 && (players[row - 1][column + 1] == "player2" || players[row - 1][column + 1] == "player2king") && players[row - 2][column + 2] == null) {
                                movement = 0;
                                selectedmoveflag = false;
                                chainb=true;


                            } else {
                                movement = 1;
                                selectedmoveflag = false;
                                chainb=false;


                            }


                        } else {
                            movement = 0;

                            selectedmoveflag = false;

                        }

                    } else if (row == Selectedplayerrow + 1 && column == SelectedPlayercol + 1) {
                        if (players[Selectedplayerrow + 1][SelectedPlayercol + 1] == null) {
                            players[Selectedplayerrow][SelectedPlayercol] = null;
                            players[Selectedplayerrow + 1][SelectedPlayercol + 1] = "player1king";

                            Log.d("SelectedMOve", "Row :" + row + " Column:" + column + " status: " + SelectedPlayer[Selectedplayerrow][SelectedPlayercol]);
                            selectedmoveflag = false;
                            movement = 1;
                            chainb=false;


                        } else {
                            selectedmoveflag = false;

                        }

                    } else if (row == Selectedplayerrow - 1 && column == SelectedPlayercol + 1) {
                        if (players[Selectedplayerrow - 1][SelectedPlayercol + 1] == null) {
                            players[Selectedplayerrow][SelectedPlayercol] = null;
                            players[Selectedplayerrow - 1][SelectedPlayercol + 1] = "player1king";

                            Log.d("SelectedMOve", "Row :" + row + " Column:" + column + " status: " + SelectedPlayer[Selectedplayerrow][SelectedPlayercol]);
                            selectedmoveflag = false;
                            movement = 1;
                            chainb=false;


                        } else {
                            selectedmoveflag = false;

                        }

                    }


                    //forward right king capture
                    else if (row == Selectedplayerrow + 2 && column == SelectedPlayercol + 2) {
                        if ((players[Selectedplayerrow + 1][SelectedPlayercol + 1] == "player2" || players[Selectedplayerrow + 1][SelectedPlayercol + 1] == "player2king") && players[Selectedplayerrow + 2][SelectedPlayercol + 2] == null) {
                            players[Selectedplayerrow][SelectedPlayercol] = null;
                            players[Selectedplayerrow + 2][SelectedPlayercol + 2] = "player1king";
                            players[Selectedplayerrow + 1][SelectedPlayercol + 1] = null;

                            row = Selectedplayerrow + 2;
                            column = SelectedPlayercol + 2;
                            if (row < 6 && column < 6 && (players[row + 1][column + 1] == "player2" || players[row + 1][column + 1] == "player2king") && players[row + 2][column + 2] == null) {
                                movement = 0;
                                selectedmoveflag = false;
                                chainb=true;


                            } else if (row < 6 && column > 1 && (players[row + 1][column - 1] == "player2" || players[row + 1][column - 1] == "player2king") && players[row + 2][column - 2] == null) {
                                movement = 0;
                                selectedmoveflag = false;
                                chainb=true;


                            } else {

                                movement = 1;
                                selectedmoveflag = false;
                                chainb=false;

                            }


                            Log.d("SelectedMOve", "Row :" + row + " Column:" + column + " status: " + SelectedPlayer[Selectedplayerrow][SelectedPlayercol]);

                        }

                    }
                    //reverse right king checkmate
                    else if (row == Selectedplayerrow - 2 && column == SelectedPlayercol + 2) {
                        if ((players[Selectedplayerrow - 1][SelectedPlayercol + 1] == "player2"|| players[Selectedplayerrow - 1][SelectedPlayercol + 1] == "player2king") && players[Selectedplayerrow - 2][SelectedPlayercol + 2] == null) {
                            players[Selectedplayerrow][SelectedPlayercol] = null;
                            players[Selectedplayerrow - 2][SelectedPlayercol + 2] = "player1king";
                            players[Selectedplayerrow - 1][SelectedPlayercol + 1] = null;

                            row = Selectedplayerrow - 2;
                            column = SelectedPlayercol + 2;
                            if (row > 1 && column < 6 && (players[row - 1][column + 1] == "player2" || players[row - 1][column + 1] == "player2king") && players[row - 2][column + 2] == null) {
                                movement = 0;
                                selectedmoveflag = false;
                                chainb=true;


                            } else if (row > 1 && column > 1 && (players[row - 1][column - 1] == "player2" || players[row - 1][column - 1] == "player2king") && players[row - 2][column - 2] == null) {
                                movement = 0;
                                selectedmoveflag = false;
                                chainb=true;


                            } else {

                                movement = 1;
                                selectedmoveflag = false;
                                chainb=false;

                            }


                            Log.d("SelectedMOve", "Row :" + row + " Column:" + column + " status: " + SelectedPlayer[Selectedplayerrow][SelectedPlayercol]);

                        }

                    } else {

                        movement = 0;

                        selectedmoveflag = false;

                    }


                }

            }


            //For clearing allowed moves
            for (int i = 0; i < numbRows; i++) {
                for (int k = 0; k < numbColumns; k++) {
                    Selectedmove[i][k] = null;

                }
            }

            invalidate();
        }

        return true;
    }
}

