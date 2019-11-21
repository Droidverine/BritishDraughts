package com.droidverine.draughtsmd;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

// Green player 2 == 1
//Blue player 1 ==0
// Green king moves forward (BAck to origin) with forced moves
public class DraughtsView extends View {
    int numbRows;
    int numbColumns;
    Paint paint;
    int width;
    int numberofwhitedraughts;
    private boolean[][] cellChecked;
    private String[][] players;
    private String[][] moveable;
    private String[][] moveableplayer1;
    int cellHeight;
    int cellWidth;
    int movement = 1;
    boolean selectedmoveflag;
    int highlight = 0;
    private boolean[][] highlightarr;
    private String[][] Selectedmove;
    private String[][] SelectedPlayer;
    int Selectedplayerrow, SelectedPlayercol;
    int init = 0;
    int height;


    public DraughtsView(Context context) {
        super(context);
        init(null);

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
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        numbColumns = 8;
        numbRows = 8;
        numberofwhitedraughts = 12;
        highlightarr = new boolean[numbRows][numbColumns];
        players = new String[numbRows][numbColumns];
        Selectedmove = new String[numbRows][numbColumns];
        SelectedPlayer = new String[numbRows][numbColumns];
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

        cellHeight = width / numbRows;
        cellWidth = height / numbColumns;
        cellChecked = new boolean[numbRows][numbColumns];

        for (int col = 0; col < numbColumns; col++) {
            for (int row = 0; row < numbRows; row++) {
                for (int whitedraugt = 0; whitedraugt < numberofwhitedraughts; whitedraugt++) {
                    Log.d("white", "" + whitedraugt);


                    if (col % 2 == 0) {
                        if (row % 2 == 0) {
                            paint.setColor(Color.WHITE);
                            canvas.drawRect(col * cellWidth, row *
                                    cellHeight, (col + 1) * cellWidth, (row +
                                    1) * cellHeight, paint);


                        } else {
                            paint.setColor(Color.BLACK);
                            canvas.drawRect(col * cellWidth, row *
                                    cellHeight, (col + 1) * cellWidth, (row +
                                    1) * cellHeight, paint);


                        }
                    } else {
                        if (row % 2 == 0) {
                            paint.setColor(Color.BLACK);
                            canvas.drawRect(col * cellWidth, row *
                                    cellHeight, (col + 1) * cellWidth, (row +
                                    1) * cellHeight, paint);


                        } else {
                            paint.setColor(Color.WHITE);
                            canvas.drawRect(col * cellWidth, row *
                                    cellHeight, (col + 1) * cellWidth, (row +
                                    1) * cellHeight, paint);

                        }
                    }
                }
            }

        }

        //player drawing
        if (init == 0) {

            canvas.translate(0, 0);

            for (int row = 0; row < numbRows; row++) {
                for (int col = 0; col < numbColumns; col++) {
                    if (players[row][col] == "player1") {


                        RectF rect = new RectF(col * cellHeight, row * cellHeight,
                                (col + 1) * cellWidth, (row +
                                1) * cellHeight);
                        //  players[row][col] = "white";
                        paint.setColor(Color.BLUE);

                        canvas.drawOval(rect, paint);
                    } else if (players[row][col] == "player2") {


                        RectF rect = new RectF(col * cellHeight, row * cellHeight,
                                (col + 1) * cellWidth, (row +
                                1) * cellHeight);
                        //  players[row][col] = "white";
                        paint.setColor(Color.GREEN);

                        canvas.drawOval(rect, paint);
                    }
                    Log.d("Players", " row " + row + " col:" + col + " " + players[row][col]);
                }

            }

            Log.d("init", "init =" + init);
            init = 1;

        } else if (init == 1) {
            Log.d("countinue", "init =" + init);
            for (int row = 0; row < numbRows; row++) {
                for (int col = 0; col < numbColumns; col++) {
                    if (players[row][col] == "player1") {


                        RectF rect = new RectF(col * cellHeight, row * cellHeight,
                                (col + 1) * cellWidth, (row +
                                1) * cellHeight);
                        //  players[row][col] = "white";
                        paint.setColor(Color.BLUE);


                        canvas.drawOval(rect, paint);

                    }
                    //King color Logic//

                    else if (players[row][col] == "player2king") {
                        RectF rect = new RectF(col * cellHeight, row * cellHeight,
                                (col + 1) * cellWidth, (row +
                                1) * cellHeight);
                        paint.setColor(Color.GREEN);

                        //  players[row][col] = "white";
/*
                        RectF rect1 = new RectF(col * (cellHeight+5), row * (cellHeight+5),
                                (col+0.5f) * cellWidth, (row+0.5f) * cellHeight);
                        paint.setColor(Color.BLUE);


*/
                        canvas.drawOval(rect, paint);
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
                        paint.setColor(Color.BLUE);

                        //  players[row][col] = "white";
/*
                        RectF rect1 = new RectF(col * (cellHeight+5), row * (cellHeight+5),
                                (col+0.5f) * cellWidth, (row+0.5f) * cellHeight);
                        paint.setColor(Color.BLUE);


*/
                        canvas.drawOval(rect, paint);

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

                        canvas.drawOval(rect, paint);
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
                        Log.d("movement", "" + Selectedmove[row][col]);


                        selectedmoveflag = true;


                    }

                    Log.d("Players", " row " + row + " col:" + col + " " + players[row][col]);
                }

            }


        }


    }

    // Green player 2 == 1
    //Blue player 1 ==0
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Boolean comp = false;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {


            int column = (int) (event.getX() / cellWidth);
            int row = (int) (event.getY() / cellHeight);

            // to check player2s & force move
            //need to set player movemable or note
            if (movement == 1) {
                for (int i = 0; i < numbRows; i++) {
                    for (int j = 0; j < numbColumns; j++) {
                        if (players[i][j] == "player2") {
                            if (i != 0 && j != 0 && players[i - 1][j - 1] == "player1" && i > 1 && j > 1 && players[i - 2][j - 2] == null) {

                                moveable[i][j] = "must";
                                comp = true;
                                Log.d("Green cmp", "Row :" + i + " Column:" + j + " status: " + players[i][j]);

                            } else if (i != 0 && j != 7 && players[i - 1][j + 1] == "player1" && i > 1 && j < 6 && players[i - 2][j + 2] == null) {

                                moveable[i][j] = "must";
                                comp = true;
                                Log.d("Green cmp", "Row :" + i + " Column:" + j + " status: " + players[i][j]);

                            }


                        } else if (players[i][j] == "player2king") {
                            //   Log.d("King cmp", "Row :" + i + " Column:" + j + " status: " + players[i][j]);

                            if (i < 6 && j > 1 && players[i + 1][j - 1] == "player1" && players[i + 2][j - 2] == null) {

                                moveable[i][j] = "must";
                                comp = true;
                                Log.d("King cmp", "Row :" + i + " Column:" + j + " status: " + players[i][j]);

                            } else if (i < 7 && j < 6 && players[i + 1][j + 1] == "player1" && players[i + 2][j + 2] == null) {

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
                            if (players[row][column] == "player2" && row > 1 && column < 6 && players[row - 1][column + 1] == "player1" && players[row - 2][column + 2] == null) {
                                SelectedPlayer[row][column] = players[row][column];
                                Selectedplayerrow = row;
                                SelectedPlayercol = column;
                                highlightarr[row - 2][column + 2] = true;
                                highlight++;
                                Log.d("GreenCheck", "Blue mila " + players[row - 2][column + 2]);


                            } else {
                                if (players[row][column] == "player2" && row > 0 && players[row - 1][column + 1] == null) {
                                    SelectedPlayer[row][column] = players[row][column];
                                    Selectedplayerrow = row;
                                    SelectedPlayercol = column;
                                    highlightarr[row - 1][column + 1] = true;
                                    highlight++;

                                }
                            }
                        } else if (players[row][column] == "player2king") {

                            if (row < 6 && column < 6 && players[row + 1][column + 1] == "player1" && players[row + 2][column + 2] == null) {
                                SelectedPlayer[row][column] = players[row][column];
                                Selectedplayerrow = row;
                                SelectedPlayercol = column;
                                highlightarr[row + 2][column + 2] = true;
                                highlight++;
                                Log.d("King Check", "Blue mila " + players[row - 2][column + 2]);


                            } else {
                                if (row < 7 && players[row + 1][column + 1] == null) {
                                    SelectedPlayer[row][column] = players[row][column];
                                    Selectedplayerrow = row;
                                    SelectedPlayercol = column;
                                    highlightarr[row + 1][column + 1] = true;
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

                            } else if (row > 1 && column > 0 && players[row - 1][column - 1] == "player1" && column != 1 && players[row - 2][column - 2] == null) {
                                SelectedPlayer[row][column] = players[row][column];
                                Selectedplayerrow = row;
                                SelectedPlayercol = column;
                                highlightarr[row - 2][column - 2] = true;
                                highlight++;
                                Log.d("GreenCheck", "Blue mila " + players[row - 2][column - 2]);

                            }


                        } else if (players[row][column] == "player2king") {
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
                        }

                    }

                }

                if (comp == true && moveable[row][column] == "must") {
                    if (players[row][column] == "player2") {
                        if (column != 7) {

///////Green Right

                            if (row > 1 && column < 6 && players[row - 1][column + 1] == "player1" && players[row - 2][column + 2] == null) {
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

                            if (row > 1 && column > 0 && players[row - 1][column - 1] == "player1" && column != 1 && players[row - 2][column - 2] == null) {
                                SelectedPlayer[row][column] = players[row][column];
                                Selectedplayerrow = row;
                                SelectedPlayercol = column;
                                highlightarr[row - 2][column - 2] = true;
                                highlight++;
                                Log.d("GreenCheck", "Blue mila " + players[row - 2][column - 2]);

                            }


                        }


                    } else if (players[row][column] == "player2king") {
                        if (column != 7) {

///////Green Right

                            if (row < 7 && column < 6 && players[row + 1][column + 1] == "player1" && players[row + 2][column + 2] == null) {
                                SelectedPlayer[row][column] = players[row][column];
                                Selectedplayerrow = row;
                                SelectedPlayercol = column;
                                highlightarr[row + 2][column + 2] = true;
                                highlight++;
                                //  Log.d("GreenCheck", "Blue mila " + players[row - 2][column + 2]);


                            }

                        }
                        if (column != 0) {

///////Green LEFTgIT

                            if (row < 7 && column > 0 && players[row + 1][column - 1] == "player1" && column != 1 && players[row + 2][column - 2] == null) {
                                SelectedPlayer[row][column] = players[row][column];
                                Selectedplayerrow = row;
                                SelectedPlayercol = column;
                                highlightarr[row + 2][column - 2] = true;
                                highlight++;
                                //     Log.d("GreenCheck", "Blue mila " + players[row - 2][column - 2]);

                            }


                        }


                    }

                }
                for (int i = 0; i < numbRows; i++) {
                    for (int j = 0; j < numbColumns; j++) {
                        moveableplayer1[row][column] = null;

                    }
                }
                comp = false;

            }

// To check Blue & force move
            //need to set player movemable or note
            else if (movement == 0 && players[row][column] == "player1") {
                Log.d("BLue bhetla", "player1");

                for (int i = 0; i < numbRows; i++) {

                    for (int j = 0; j < numbColumns; j++) {
                        if (players[i][j] == "player1") {
                            if (i != 7 && j != 0 && (players[i + 1][j - 1] == "player2" || players[i + 1][j - 1] == "player2king") && i < 6 && j > 1 && players[i + 2][j - 2] == null) {

                                moveableplayer1[i][j] = "must";
                                comp = true;
                                Log.d("BLue bhetla", "Row :" + i + " Column:" + j + " status: " + players[i][j]);

                            } else if (i != 7 && j != 7 && (players[i + 1][j + 1] == "player2" || players[i + 1][j + 1] == "player2king") && i < 6 && j < 6 && players[i + 2][j + 2] == null) {

                                moveableplayer1[i][j] = "must";
                                comp = true;
                                Log.d("BLue bhetla", "Row :" + i + " Column:" + j + " status: " + players[i][j]);

                            } else {
                                Log.d("BLue bhetla", "Nothing");

                            }


                        }
                    }
                }
                //New Highlight
                if (comp != true && moveableplayer1[row][column] != "must" && movement == 0) {
                    Log.d("BlueMove", "Row :" + row + " Column:" + column + " status: " + players[row][column]);
                    if (column != 7 && row != 7) {
                        if (players[row][column] == "player1" && players[row + 1][column + 1] == null) {
                            Log.d("Bluecheck", "left check " + players[row + 1][column + 1]);
                            highlightarr[row + 1][column + 1] = true;
                            highlight++;
                            SelectedPlayer[row][column] = players[row][column];
                            Selectedplayerrow = row;
                            SelectedPlayercol = column;
                        } else if (players[row][column] == "player1" && column < 6 && row < 6 && (players[row + 1][column + 1] == "player2" || players[row + 1][column + 1] == "player2king") && players[row + 2][column + 2] == null) {


                            Log.d("Bluecheck", "left check " + players[row + 1][column + 1]);
                            highlightarr[row + 2][column + 2] = true;
                            highlight++;
                            SelectedPlayer[row][column] = players[row][column];
                            Selectedplayerrow = row;
                            SelectedPlayercol = column;


                        }


                    }
                    if (column != 0 && row != 7) {
                        if (players[row][column] == "player1" && players[row + 1][column - 1] == null) {
                            Log.d("Blackcheck", "right check " + players[row + 1][column - 1]);

                            highlightarr[row + 1][column - 1] = true;
                            highlight++;
                            SelectedPlayer[row][column] = players[row][column];
                            Selectedplayerrow = row;
                            SelectedPlayercol = column;

                        } else if (players[row][column] == "player1" && column > 1 && row < 6 && (players[row + 1][column - 1] == "player2" || players[row + 1][column - 1] == "player2king") && column != 1 && players[row + 2][column - 2] == null) {
                            highlightarr[row + 2][column - 2] = true;
                            highlight++;
                            SelectedPlayer[row][column] = players[row][column];
                            Selectedplayerrow = row;
                            SelectedPlayercol = column;

                        }

                    }


                }

                if (comp == true && moveableplayer1[row][column] == "must") {
                    if (column != 7) {

///////Blue Right

                        if (row < 6 && column < 6 && (players[row + 1][column + 1] == "player2" || players[row + 1][column + 1] == "player2king") && players[row + 2][column + 2] == null) {
                            SelectedPlayer[row][column] = players[row][column];
                            Selectedplayerrow = row;
                            SelectedPlayercol = column;
                            highlightarr[row + 2][column + 2] = true;
                            highlight++;
                            Log.d("BLue", "Blue mila " + players[row + 2][column + 2]);


                        }

                    }
                    if (column != 0) {

///////Blue LEFT

                        if (row < 6 && column > 0 && (players[row + 1][column - 1] == "player2" || players[row + 1][column - 1] == "player2king") && column != 1 && players[row + 2][column - 2] == null) {
                            SelectedPlayer[row][column] = players[row][column];
                            Selectedplayerrow = row;
                            SelectedPlayercol = column;
                            highlightarr[row + 2][column - 2] = true;
                            highlight++;
                            Log.d("BLue", "Blue mila " + players[row + 2][column - 2]);

                        }


                    }


                }
                for (int i = 0; i < numbRows; i++) {
                    for (int j = 0; j < numbColumns; j++) {
                        moveableplayer1[row][column] = null;

                    }
                }
                comp = false;
            }
            Log.d("gubugubu", "" + movement);

            //King movement player1


            //For movement

            if (selectedmoveflag) {

                //Green Movement
                if (movement == 1 && Selectedmove[row][column] == "move") {

                    if (players[Selectedplayerrow][SelectedPlayercol] == "player2") {
                        //Green Left movement
                        if (row == Selectedplayerrow - 1 && column == SelectedPlayercol - 1) {
                            if (players[Selectedplayerrow - 1][SelectedPlayercol - 1] == null) {
                                players[Selectedplayerrow][SelectedPlayercol] = null;
                                players[Selectedplayerrow - 1][SelectedPlayercol - 1] = "player2";
                                Log.d("SelectedMOve", "Row :" + row + " Column:" + column + " status: " + SelectedPlayer[Selectedplayerrow][SelectedPlayercol]);
                                selectedmoveflag = false;

                                movement = 0;
                            }

                            selectedmoveflag = false;


                        } else if (row == Selectedplayerrow - 2 && column == SelectedPlayercol - 2) {
                            if (players[Selectedplayerrow - 1][SelectedPlayercol - 1] == "player1" && players[Selectedplayerrow - 2][SelectedPlayercol - 2] == null) {
                                players[Selectedplayerrow][SelectedPlayercol] = null;
                                players[Selectedplayerrow - 2][SelectedPlayercol - 2] = "player2";
                                players[Selectedplayerrow - 1][SelectedPlayercol - 1] = null;

                                ///Chaining Working here/////////
                                row = Selectedplayerrow - 2;
                                column = SelectedPlayercol - 2;
                                if (row > 1 && column > 1 && players[row - 1][column - 1] == "player1" && players[row - 2][column - 2] == null) {
                                    movement = 1;
                                    selectedmoveflag = false;

                                } else if (row > 1 && column < 6 && players[row - 1][column + 1] == "player1" && players[row - 2][column + 2] == null) {
                                    movement = 1;
                                    selectedmoveflag = false;

                                } else {
                                    movement = 0;
                                    selectedmoveflag = false;

                                }


                            } else {
                                movement = 1;

                                selectedmoveflag = false;

                            }

                        } else if (row == Selectedplayerrow - 1 && column == SelectedPlayercol + 1) {
                            if (players[Selectedplayerrow - 1][SelectedPlayercol + 1] == null) {
                                players[Selectedplayerrow][SelectedPlayercol] = null;
                                players[Selectedplayerrow - 1][SelectedPlayercol + 1] = "player2";

                                Log.d("SelectedMOve", "Row :" + row + " Column:" + column + " status: " + SelectedPlayer[Selectedplayerrow][SelectedPlayercol]);
                                selectedmoveflag = false;
                                movement = 0;

                            } else {
                                selectedmoveflag = false;

                            }

                        } else if (row == Selectedplayerrow - 2 && column == SelectedPlayercol + 2) {
                            if (players[Selectedplayerrow - 1][SelectedPlayercol + 1] == "player1" && players[Selectedplayerrow - 2][SelectedPlayercol + 2] == null) {
                                players[Selectedplayerrow][SelectedPlayercol] = null;
                                players[Selectedplayerrow - 2][SelectedPlayercol + 2] = "player2";
                                players[Selectedplayerrow - 1][SelectedPlayercol + 1] = null;

                                row = Selectedplayerrow - 2;
                                column = SelectedPlayercol + 2;
                                if (row > 1 && column < 6 && players[row - 1][column + 1] == "player1" && players[row - 2][column + 2] == null) {
                                    movement = 1;
                                    selectedmoveflag = false;

                                } else if (row > 1 && column > 1 && players[row - 1][column - 1] == "player1" && players[row - 2][column - 2] == null) {
                                    movement = 1;
                                    selectedmoveflag = false;

                                } else {

                                    movement = 0;
                                    selectedmoveflag = false;

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
                        //Green Left movement
                        if (row == Selectedplayerrow + 1 && column == SelectedPlayercol - 1) {
                            if (players[Selectedplayerrow + 1][SelectedPlayercol - 1] == null) {
                                players[Selectedplayerrow][SelectedPlayercol] = null;
                                players[Selectedplayerrow + 1][SelectedPlayercol - 1] = "player2king";
                                Log.d("SelectedMOve", "Row :" + row + " Column:" + column + " status: " + SelectedPlayer[Selectedplayerrow][SelectedPlayercol]);
                                selectedmoveflag = false;

                                movement = 0;
                            }

                            selectedmoveflag = false;


                        } else if (row == Selectedplayerrow + 2 && column == SelectedPlayercol - 2) {
                            if (players[Selectedplayerrow + 1][SelectedPlayercol - 1] == "player1" && players[Selectedplayerrow + 2][SelectedPlayercol - 2] == null) {
                                players[Selectedplayerrow][SelectedPlayercol] = null;
                                players[Selectedplayerrow + 2][SelectedPlayercol - 2] = "player2king";
                                players[Selectedplayerrow + 1][SelectedPlayercol - 1] = null;

                                ///Chaining Working here/////////
                                row = Selectedplayerrow - 2;
                                column = SelectedPlayercol - 2;
                                if (row > 1 && column > 1 && players[row + 1][column - 1] == "player1" && players[row + 2][column - 2] == null) {
                                    movement = 1;
                                    selectedmoveflag = false;

                                } else if (row > 1 && column < 6 && players[row + 1][column + 1] == "player1" && players[row + 2][column + 2] == null) {
                                    movement = 1;
                                    selectedmoveflag = false;

                                } else {
                                    movement = 0;
                                    selectedmoveflag = false;

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

                            } else {
                                selectedmoveflag = false;

                            }

                        } else if (row == Selectedplayerrow + 2 && column == SelectedPlayercol + 2) {
                            if (players[Selectedplayerrow + 1][SelectedPlayercol + 1] == "player1" && players[Selectedplayerrow + 2][SelectedPlayercol + 2] == null) {
                                players[Selectedplayerrow][SelectedPlayercol] = null;
                                players[Selectedplayerrow + 2][SelectedPlayercol + 2] = "player2king";
                                players[Selectedplayerrow + 1][SelectedPlayercol + 1] = null;

                                row = Selectedplayerrow - 2;
                                column = SelectedPlayercol + 2;
                                if (row > 1 && column < 6 && players[row + 1][column + 1] == "player1" && players[row + 2][column + 2] == null) {
                                    movement = 1;
                                    selectedmoveflag = false;

                                } else if (row > 1 && column > 1 && players[row + 1][column - 1] == "player1" && players[row + 2][column - 2] == null) {
                                    movement = 1;
                                    selectedmoveflag = false;

                                } else {

                                    movement = 0;
                                    selectedmoveflag = false;

                                }


                                Log.d("SelectedMOve", "Row :" + row + " Column:" + column + " status: " + SelectedPlayer[Selectedplayerrow][SelectedPlayercol]);

                            }

                        } else {

                            movement = 1;

                            selectedmoveflag = false;

                        }
                        if (row == 0) {
                            //  players[row][column] = "player2king";
                            //movement = 0;

                        }

                    }
                }


                //Blue Movement

                else if (movement == 0 && Selectedmove[row][column] == "move" && players[Selectedplayerrow][SelectedPlayercol] == "player1") {
                    if (row == Selectedplayerrow + 1 && column == SelectedPlayercol - 1) {
                        if (players[Selectedplayerrow + 1][SelectedPlayercol - 1] == null) {
                            players[Selectedplayerrow][SelectedPlayercol] = null;
                            players[Selectedplayerrow + 1][SelectedPlayercol - 1] = "player1";
                            Log.d("SelectedMOve", "Row :" + row + " Column:" + column + " status: " + SelectedPlayer[Selectedplayerrow][SelectedPlayercol]);
                            selectedmoveflag = false;
                            movement = 1;
                        } else {
                            selectedmoveflag = false;
                        }


                    } else if (row == Selectedplayerrow + 2 && column == SelectedPlayercol - 2) {
                        if (players[Selectedplayerrow + 1][SelectedPlayercol - 1] == "player2" && players[Selectedplayerrow + 2][SelectedPlayercol - 2] == null) {
                            players[Selectedplayerrow][SelectedPlayercol] = null;
                            players[Selectedplayerrow + 2][SelectedPlayercol - 2] = "player1";
                            players[Selectedplayerrow + 1][SelectedPlayercol - 1] = null;
                            Log.d("SelectedMOve", "Row :" + row + " Column:" + column + " status: " + SelectedPlayer[Selectedplayerrow][SelectedPlayercol]);
                            row = Selectedplayerrow + 2;
                            column = SelectedPlayercol - 2;
                            if (row < 6 && column > 1 && (players[row + 1][column - 1] == "player2" || players[row + 1][column - 1] == "player2king") && players[row + 2][column - 2] == null) {
                                movement = 0;
                                selectedmoveflag = false;

                            } else if (row < 6 && column < 6 && (players[row + 1][column + 1] == "player2" || players[row + 1][column + 1] == "player2king") && players[row + 2][column + 2] == null) {
                                movement = 0;
                                selectedmoveflag = false;

                            } else {

                                movement = 1;
                                selectedmoveflag = false;

                            }


                        } else {
                            selectedmoveflag = false;

                        }


                    } else if (row == Selectedplayerrow + 2 && column == SelectedPlayercol + 2) {
                        if ((players[Selectedplayerrow + 1][SelectedPlayercol + 1] == "player2" || players[Selectedplayerrow + 1][SelectedPlayercol + 1] == "player2king") && players[Selectedplayerrow + 2][SelectedPlayercol + 2] == null) {
                            players[Selectedplayerrow][SelectedPlayercol] = null;
                            players[Selectedplayerrow + 2][SelectedPlayercol + 2] = "player1";
                            players[Selectedplayerrow + 1][SelectedPlayercol + 1] = null;

                            Log.d("SelectedMOve", "Row :" + row + " Column:" + column + " status: " + SelectedPlayer[Selectedplayerrow][SelectedPlayercol]);
                            // selectedmoveflag = false;
                            //movement = 1;
                            row = Selectedplayerrow + 2;
                            column = SelectedPlayercol + 2;
                            if (row < 6 && column < 6 && players[row + 1][column + 1] == "player2" && players[row + 2][column + 2] == null) {
                                movement = 0;
                                selectedmoveflag = false;

                            } else if (row < 6 && column > 1 && players[row + 1][column - 1] == "player2" && players[row + 2][column - 2] == null) {
                                movement = 0;
                                selectedmoveflag = false;
                            } else {

                                movement = 1;
                                selectedmoveflag = false;

                            }

                        } else {
                            movement = 1;

                            selectedmoveflag = false;

                            //  selectedmoveflag = false;

                        }
                    } else if (row == Selectedplayerrow + 1 && column == SelectedPlayercol + 1) {
                        if (players[Selectedplayerrow + 1][SelectedPlayercol + 1] == null) {
                            players[Selectedplayerrow][SelectedPlayercol] = null;
                            players[Selectedplayerrow + 1][SelectedPlayercol + 1] = "player1";
                            Log.d("SelectedMOve", "Row :" + row + " Column:" + column + " status: " + SelectedPlayer[Selectedplayerrow][SelectedPlayercol]);
                            selectedmoveflag = false;
                            movement = 1;

                        } else {
                            selectedmoveflag = false;

                        }


                    } else {
                        selectedmoveflag = false;

                    }
                    //Make player 1 king
                    if (row == 7) {
                        players[row][column] = "player1king";
                    }
                } else {

                    selectedmoveflag = false;

                }


            } else {
            }


            invalidate();
        }

        return true;
    }
}

