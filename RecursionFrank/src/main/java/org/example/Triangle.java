package org.example;

public class Triangle
{
    public Triangle(int sideLength)
    {
        TriangleBuilder(sideLength);
    }

    private void TriangleBuilder(int s) {

        if (s == 0)
        {
            return;
        }
        else
        {
            buildLine(s);
            System.out.print("\n");
        }

        TriangleBuilder(s-1);
    }

    private void buildLine(int s)
    {
        if (s == 0)
        {
            return;
        }
        else {
            System.out.print("#");
        }
        buildLine(s - 1);
    }
}
