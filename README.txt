{\rtf1\ansi\ansicpg1252\cocoartf1504\cocoasubrtf830
{\fonttbl\f0\fswiss\fcharset0 Helvetica;}
{\colortbl;\red255\green255\blue255;}
{\*\expandedcolortbl;;}
\margl1440\margr1440\vieww10800\viewh8400\viewkind0
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\f0\fs24 \cf0 /*\
 * EightPuzzle is played on a 3-by-3 grid with 8 square blocks labeled 1 through 8 and a blank square represented\
 * as zero in this game. The goal is to rearrange the blocks so that they are in order. \
 * it is permitted to slide blocks horizontally or vertically into the blank square which is represented as zero\
 * \
 * Problem: Find a path from a start state to a goal state using "A Star" Search (A*) with Manhattan distance heuristics\
 *        \
 * Ex:\
 *  Start State (Initial Board): \
			 1 2 3\
 *                               4 5 6\
 *                               0 7 8\
 *                               \
 *  End State (Goal State):      \
			 1 2 3\
 *                               4 5 6\
 *                               7 8 0\
 *                               \
 * Solution:\
 * 1 2 3          1 2 3          1 2 3\
 * 4 5 6  ==> 	4 5 6  ==>  4 5 6\
 * 0 7 8          7 0 8          7 8 0   \
 * \
 *  Author: Viswanath Kasireddy                  \
 */}