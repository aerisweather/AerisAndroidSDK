package com.example.demoaerisproject.data.weather.model;

import com.aerisweather.aeris.model.AerisLocation;
import com.aerisweather.aeris.model.Moon;
import com.aerisweather.aeris.model.Sun;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b&\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BW\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u00a2\u0006\u0002\u0010\u0010J\t\u0010-\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010.\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010/\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\u000b\u00100\u001a\u0004\u0018\u00010\tH\u00c6\u0003J\u000b\u00101\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003J\u000b\u00102\u001a\u0004\u0018\u00010\rH\u00c6\u0003J\u000b\u00103\u001a\u0004\u0018\u00010\u000fH\u00c6\u0003J[\u00104\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u00c6\u0001J\u0013\u00105\u001a\u0002062\b\u00107\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00108\u001a\u000209H\u00d6\u0001J\t\u0010:\u001a\u00020\u0005H\u00d6\u0001R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u001c\u0010\b\u001a\u0004\u0018\u00010\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001c\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b\'\u0010(R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,\u00a8\u0006;"}, d2 = {"Lcom/example/demoaerisproject/data/weather/model/SunMoon;", "", "timestamp", "", "dateTimeISO", "", "loc", "Lcom/aerisweather/aeris/model/AerisLocation;", "place", "Lcom/example/demoaerisproject/data/weather/model/Place;", "profile", "Lcom/example/demoaerisproject/data/weather/model/Profile;", "sun", "Lcom/aerisweather/aeris/model/Sun;", "moon", "Lcom/aerisweather/aeris/model/Moon;", "(JLjava/lang/String;Lcom/aerisweather/aeris/model/AerisLocation;Lcom/example/demoaerisproject/data/weather/model/Place;Lcom/example/demoaerisproject/data/weather/model/Profile;Lcom/aerisweather/aeris/model/Sun;Lcom/aerisweather/aeris/model/Moon;)V", "getDateTimeISO", "()Ljava/lang/String;", "setDateTimeISO", "(Ljava/lang/String;)V", "getLoc", "()Lcom/aerisweather/aeris/model/AerisLocation;", "setLoc", "(Lcom/aerisweather/aeris/model/AerisLocation;)V", "getMoon", "()Lcom/aerisweather/aeris/model/Moon;", "setMoon", "(Lcom/aerisweather/aeris/model/Moon;)V", "getPlace", "()Lcom/example/demoaerisproject/data/weather/model/Place;", "setPlace", "(Lcom/example/demoaerisproject/data/weather/model/Place;)V", "getProfile", "()Lcom/example/demoaerisproject/data/weather/model/Profile;", "setProfile", "(Lcom/example/demoaerisproject/data/weather/model/Profile;)V", "getSun", "()Lcom/aerisweather/aeris/model/Sun;", "setSun", "(Lcom/aerisweather/aeris/model/Sun;)V", "getTimestamp", "()J", "setTimestamp", "(J)V", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class SunMoon {
    private long timestamp;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String dateTimeISO;
    @org.jetbrains.annotations.Nullable()
    private com.aerisweather.aeris.model.AerisLocation loc;
    @org.jetbrains.annotations.Nullable()
    private com.example.demoaerisproject.data.weather.model.Place place;
    @org.jetbrains.annotations.Nullable()
    private com.example.demoaerisproject.data.weather.model.Profile profile;
    @org.jetbrains.annotations.Nullable()
    private com.aerisweather.aeris.model.Sun sun;
    @org.jetbrains.annotations.Nullable()
    private com.aerisweather.aeris.model.Moon moon;
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.demoaerisproject.data.weather.model.SunMoon copy(long timestamp, @org.jetbrains.annotations.Nullable()
    java.lang.String dateTimeISO, @org.jetbrains.annotations.Nullable()
    com.aerisweather.aeris.model.AerisLocation loc, @org.jetbrains.annotations.Nullable()
    com.example.demoaerisproject.data.weather.model.Place place, @org.jetbrains.annotations.Nullable()
    com.example.demoaerisproject.data.weather.model.Profile profile, @org.jetbrains.annotations.Nullable()
    com.aerisweather.aeris.model.Sun sun, @org.jetbrains.annotations.Nullable()
    com.aerisweather.aeris.model.Moon moon) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public SunMoon() {
        super();
    }
    
    public SunMoon(long timestamp, @org.jetbrains.annotations.Nullable()
    java.lang.String dateTimeISO, @org.jetbrains.annotations.Nullable()
    com.aerisweather.aeris.model.AerisLocation loc, @org.jetbrains.annotations.Nullable()
    com.example.demoaerisproject.data.weather.model.Place place, @org.jetbrains.annotations.Nullable()
    com.example.demoaerisproject.data.weather.model.Profile profile, @org.jetbrains.annotations.Nullable()
    com.aerisweather.aeris.model.Sun sun, @org.jetbrains.annotations.Nullable()
    com.aerisweather.aeris.model.Moon moon) {
        super();
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final long getTimestamp() {
        return 0L;
    }
    
    public final void setTimestamp(long p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getDateTimeISO() {
        return null;
    }
    
    public final void setDateTimeISO(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.aerisweather.aeris.model.AerisLocation component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.aerisweather.aeris.model.AerisLocation getLoc() {
        return null;
    }
    
    public final void setLoc(@org.jetbrains.annotations.Nullable()
    com.aerisweather.aeris.model.AerisLocation p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.example.demoaerisproject.data.weather.model.Place component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.example.demoaerisproject.data.weather.model.Place getPlace() {
        return null;
    }
    
    public final void setPlace(@org.jetbrains.annotations.Nullable()
    com.example.demoaerisproject.data.weather.model.Place p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.example.demoaerisproject.data.weather.model.Profile component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.example.demoaerisproject.data.weather.model.Profile getProfile() {
        return null;
    }
    
    public final void setProfile(@org.jetbrains.annotations.Nullable()
    com.example.demoaerisproject.data.weather.model.Profile p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.aerisweather.aeris.model.Sun component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.aerisweather.aeris.model.Sun getSun() {
        return null;
    }
    
    public final void setSun(@org.jetbrains.annotations.Nullable()
    com.aerisweather.aeris.model.Sun p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.aerisweather.aeris.model.Moon component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.aerisweather.aeris.model.Moon getMoon() {
        return null;
    }
    
    public final void setMoon(@org.jetbrains.annotations.Nullable()
    com.aerisweather.aeris.model.Moon p0) {
    }
}