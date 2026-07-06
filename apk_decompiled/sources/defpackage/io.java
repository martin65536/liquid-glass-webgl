package defpackage;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class io {
    public static final io e;
    public static final io f;
    public static final io g;
    public static final /* synthetic */ io[] h;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Enum, io] */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Enum, io] */
    /* JADX WARN: Type inference failed for: r3v1, types: [java.lang.Enum, io] */
    static {
        ?? r0 = new Enum("Yes", 0);
        e = r0;
        ?? r1 = new Enum("No", 1);
        f = r1;
        ?? r3 = new Enum("NotInitialized", 2);
        g = r3;
        h = new io[]{r0, r1, r3};
    }

    public static io valueOf(String str) {
        return (io) Enum.valueOf(io.class, str);
    }

    public static io[] values() {
        return (io[]) h.clone();
    }
}
