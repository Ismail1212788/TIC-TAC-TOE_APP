package com.example.ti

import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {

    private var currentPlayer = 1
    private var cells: Array<IntArray> = Array(3) { IntArray(3) }
    private lateinit var gridLayout: GridLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gridLayout = findViewById(R.id.gridLayout)
        resetGame()
    }

    fun onCellClick(view: View) {
        val cell = view as ImageButton
        val row = cell.tag.toString().toInt() / 3
        val col = cell.tag.toString().toInt() % 3

        if (cells[row][col] == 0) {
            cells[row][col] = currentPlayer
            cell.setImageResource(if (currentPlayer == 1) R.drawable.x else R.drawable.o)
            cell.isEnabled = false

            if (checkForWin(currentPlayer)) {
                showWinMessage(currentPlayer)
            } else if (checkForDraw()) {
                showDrawMessage()
            } else {
                currentPlayer = 3 - currentPlayer
            }
        }
        if (cells[row][col] == 0) {
            cells[row][col] = currentPlayer
            cell.setImageResource(if (currentPlayer == 1) R.drawable.x else R.drawable.o)
            cell.isEnabled = false

            if (checkForWin(currentPlayer)) {
                showWinMessage(currentPlayer)
            } else if (checkForDraw()) {
                showDrawMessage()
            } else {
                currentPlayer = 3 - currentPlayer
            }
        }
    }

    fun onResetClick(view: View) {
        resetGame()
    }

    private fun checkForWin(player: Int): Boolean {

        for (i in 0..2) {
            if (cells[i][0] == player && cells[i][1] == player && cells[i][2] == player) {
                return true
            }
            if (cells[0][i] == player && cells[1][i] == player && cells[2][i] == player) {
                return true
            }
        }
        if (cells[0][0] == player && cells[1][1] == player && cells[2][2] == player) {
            return true
        }
        if (cells[0][2] == player && cells[1][1] == player && cells[2][0] == player) {
            return true
        }
        return false
    }

    private fun checkForDraw(): Boolean {
        return cells.all { row -> row.all { cell -> cell != 0 } }
    }

    private fun showWinMessage(player: Int) {
        if (player == 1) {
            val i=findViewById<TextView>(R.id.textView6)
            var v=findViewById<TextView>(R.id.textView6).text.toString().toInt()+1
            i.text=v.toString()
        }
        else {
            val i=findViewById<TextView>(R.id.textView3)
            var v=findViewById<TextView>(R.id.textView3).text.toString().toInt()+1
            i.text=v.toString()

        }
        val message = getString(if (player == 1) R.string.player_x_wins else R.string.player_o_wins)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        disableAllCells()
    }

    private fun showDrawMessage() {
        Toast.makeText(this, R.string.draw, Toast.LENGTH_SHORT).show()
        disableAllCells()
    }

    private fun disableAllCells() {
        gridLayout.children.forEach { view ->
            if (view is ImageButton) {
                view.isEnabled = false
            }
        }
    }

    private fun resetGame() {
        gridLayout.children.forEach { view ->
            if (view is ImageButton) {
                view.setImageResource(0)
                view.isEnabled = true
            }
        }
        currentPlayer = 1
        cells = Array(3) { IntArray(3) }
    }
}
