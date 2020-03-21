/*
 * This file is generated by jOOQ.
 */
package de.wirvsvirus.zentralesmelderegister.model.jooq.tables.records;


import de.wirvsvirus.zentralesmelderegister.model.jooq.tables.State;

import java.math.BigDecimal;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class StateRecord extends UpdatableRecordImpl<StateRecord> implements Record4<Long, BigDecimal, BigDecimal, String> {

    private static final long serialVersionUID = 588558994;

    /**
     * Setter for <code>public.state.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.state.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.state.coordinates_longitude</code>.
     */
    public void setCoordinatesLongitude(BigDecimal value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.state.coordinates_longitude</code>.
     */
    public BigDecimal getCoordinatesLongitude() {
        return (BigDecimal) get(1);
    }

    /**
     * Setter for <code>public.state.coordinates_latitude</code>.
     */
    public void setCoordinatesLatitude(BigDecimal value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.state.coordinates_latitude</code>.
     */
    public BigDecimal getCoordinatesLatitude() {
        return (BigDecimal) get(2);
    }

    /**
     * Setter for <code>public.state.name</code>.
     */
    public void setName(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.state.name</code>.
     */
    public String getName() {
        return (String) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Long, BigDecimal, BigDecimal, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Long, BigDecimal, BigDecimal, String> valuesRow() {
        return (Row4) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return State.STATE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field2() {
        return State.STATE.COORDINATES_LONGITUDE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field3() {
        return State.STATE.COORDINATES_LATITUDE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return State.STATE.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component2() {
        return getCoordinatesLongitude();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component3() {
        return getCoordinatesLatitude();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value2() {
        return getCoordinatesLongitude();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value3() {
        return getCoordinatesLatitude();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StateRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StateRecord value2(BigDecimal value) {
        setCoordinatesLongitude(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StateRecord value3(BigDecimal value) {
        setCoordinatesLatitude(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StateRecord value4(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StateRecord values(Long value1, BigDecimal value2, BigDecimal value3, String value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached StateRecord
     */
    public StateRecord() {
        super(State.STATE);
    }

    /**
     * Create a detached, initialised StateRecord
     */
    public StateRecord(Long id, BigDecimal coordinatesLongitude, BigDecimal coordinatesLatitude, String name) {
        super(State.STATE);

        set(0, id);
        set(1, coordinatesLongitude);
        set(2, coordinatesLatitude);
        set(3, name);
    }
}
