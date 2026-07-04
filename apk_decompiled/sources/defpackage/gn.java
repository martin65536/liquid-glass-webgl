package defpackage;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class gn {
    public static final gn e;
    public static final gn f;
    public static final gn g;
    public static final /* synthetic */ gn[] h;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Enum, gn] */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Enum, gn] */
    /* JADX WARN: Type inference failed for: r3v1, types: [java.lang.Enum, gn] */
    static {
        ?? r0 = new Enum("Vertical", 0);
        e = r0;
        ?? r1 = new Enum("Horizontal", 1);
        f = r1;
        ?? r3 = new Enum("Both", 2);
        g = r3;
        h = new gn[]{r0, r1, r3};
    }

    public static gn valueOf(String str) {
        return (gn) Enum.valueOf(gn.class, str);
    }

    public static gn[] values() {
        return (gn[]) h.clone();
    }
}
