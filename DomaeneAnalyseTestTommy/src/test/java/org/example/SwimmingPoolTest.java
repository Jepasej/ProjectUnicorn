package org.example;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class SwimmingPoolTest {

    @Test
    public void testChildInAdultPensionerOut()
    {
        SwimmingPool pool = new SwimmingPool();
        LocalDate dob = LocalDate.of(1990, 1, 1);
        LocalDate today = LocalDate.of(2000, 1, 1);

        int ticketPrice = pool.ticketPrice(dob, today);

        assertEquals(30, ticketPrice);
        assertNotEquals(60, ticketPrice);
    }
/*
    @Test
    public void testAdultInChildOut ()
    {
        SwimmingPool pool = new SwimmingPool();
        Date dob = new Date(1990, 1, 1);
        Date today = new Date(2020, 1, 1);

        int ticketPrice = pool.ticketPrice(dob, today);

        assertEquals(60, ticketPrice);
        assertNotEquals(30, ticketPrice);
    }

    @Test
    public void testPensionerIn()
    {
        SwimmingPool pool = new SwimmingPool();
        Date dob = new Date(1955,1,1);
        Date today = new Date(2025,1,1);

        int ticketPrice = pool.ticketPrice(dob, today);

        assertEquals(40, ticketPrice);
        assertNotEquals(60, ticketPrice);
    }
*/
    @Test
    public void testChildOnAdultOff()
    {
        SwimmingPool pool = new SwimmingPool();
        LocalDate dob = LocalDate.of(1989, 1, 1);
        LocalDate today = LocalDate.of(2000, 12, 31);

        int ticketPrice = pool.ticketPrice(dob, today);

        assertEquals(30, ticketPrice);
        assertNotEquals(60, ticketPrice);
    }/*

    @Test
    public void testAdultOnChildOff()
    {
        SwimmingPool pool = new SwimmingPool();
        Date dob = new Date(1990, 1, 1);
        Date today = new Date(2002, 1, 1);

        int ticketPrice = pool.ticketPrice(dob, today);

        assertEquals(60, ticketPrice);
        assertNotEquals(30, ticketPrice);
    }

    @Test
    public void testPensionerOn()
    {
        SwimmingPool pool = new SwimmingPool();
        Date dob = new Date(1960,1,1);
        Date today = new Date(2025,1,1);

        int ticketPrice = pool.ticketPrice(dob, today);

        assertEquals(40, ticketPrice);
        assertNotEquals(60, ticketPrice);
    }

    @Test
    public void testPensionerOff()
    {
        SwimmingPool pool = new SwimmingPool();
        Date dob = new Date(1960,1,1);
        Date today = new Date(2024,12,31);

        int ticketPrice = pool.ticketPrice(dob, today);

        assertEquals(60, ticketPrice);
        assertNotEquals(40, ticketPrice);
    }*/
}