package defpackage;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class xb {
    public static final xb e;
    public static final xb f;
    public static final xb g;
    public static final /* synthetic */ xb[] h;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Enum, xb] */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Enum, xb] */
    /* JADX WARN: Type inference failed for: r3v1, types: [java.lang.Enum, xb] */
    static {
        ?? r0 = new Enum("SUSPEND", 0);
        e = r0;
        ?? r1 = new Enum("DROP_OLDEST", 1);
        f = r1;
        ?? r3 = new Enum("DROP_LATEST", 2);
        g = r3;
        h = new xb[]{r0, r1, r3};
    }

    public static xb valueOf(String str) {
        return (xb) Enum.valueOf(xb.class, str);
    }

    public static xb[] values() {
        return (xb[]) h.clone();
    }
}
