/*
 * This file is generated by jOOQ.
 */
package de.wirvsvirus.zentralesmelderegister.model.jooq.tables;


import de.wirvsvirus.zentralesmelderegister.model.jooq.Indexes;
import de.wirvsvirus.zentralesmelderegister.model.jooq.Keys;
import de.wirvsvirus.zentralesmelderegister.model.jooq.Public;
import de.wirvsvirus.zentralesmelderegister.model.jooq.tables.records.CityRecord;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


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
public class City extends TableImpl<CityRecord> {

    private static final long serialVersionUID = -512725467;

    /**
     * The reference instance of <code>public.city</code>
     */
    public static final City CITY = new City();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CityRecord> getRecordType() {
        return CityRecord.class;
    }

    /**
     * The column <code>public.city.id</code>.
     */
    public final TableField<CityRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('city_id_seq'::regclass)", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>public.city.coordinates_longitude</code>.
     */
    public final TableField<CityRecord, BigDecimal> COORDINATES_LONGITUDE = createField("coordinates_longitude", org.jooq.impl.SQLDataType.NUMERIC, this, "");

    /**
     * The column <code>public.city.coordinates_latitude</code>.
     */
    public final TableField<CityRecord, BigDecimal> COORDINATES_LATITUDE = createField("coordinates_latitude", org.jooq.impl.SQLDataType.NUMERIC, this, "");

    /**
     * The column <code>public.city.name</code>.
     */
    public final TableField<CityRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.city.country_id</code>.
     */
    public final TableField<CityRecord, Long> COUNTRY_ID = createField("country_id", org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * Create a <code>public.city</code> table reference
     */
    public City() {
        this(DSL.name("city"), null);
    }

    /**
     * Create an aliased <code>public.city</code> table reference
     */
    public City(String alias) {
        this(DSL.name(alias), CITY);
    }

    /**
     * Create an aliased <code>public.city</code> table reference
     */
    public City(Name alias) {
        this(alias, CITY);
    }

    private City(Name alias, Table<CityRecord> aliased) {
        this(alias, aliased, null);
    }

    private City(Name alias, Table<CityRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> City(Table<O> child, ForeignKey<O, CityRecord> key) {
        super(child, key, CITY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.CITY_ID_UINDEX, Indexes.CITY_PK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<CityRecord, Long> getIdentity() {
        return Keys.IDENTITY_CITY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<CityRecord> getPrimaryKey() {
        return Keys.CITY_PK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<CityRecord>> getKeys() {
        return Arrays.<UniqueKey<CityRecord>>asList(Keys.CITY_PK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<CityRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<CityRecord, ?>>asList(Keys.CITY__CITY_COUNTY_ID_FK);
    }

    public County county() {
        return new County(this, Keys.CITY__CITY_COUNTY_ID_FK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public City as(String alias) {
        return new City(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public City as(Name alias) {
        return new City(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public City rename(String name) {
        return new City(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public City rename(Name name) {
        return new City(name, null);
    }
}
