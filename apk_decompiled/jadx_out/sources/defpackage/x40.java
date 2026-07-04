package defpackage;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class x40 {
    public static final x40 e;
    public static final x40 f;
    public static final x40 g;
    public static final /* synthetic */ x40[] h;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Enum, x40] */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Enum, x40] */
    /* JADX WARN: Type inference failed for: r3v1, types: [java.lang.Enum, x40] */
    static {
        ?? r0 = new Enum("InMeasureBlock", 0);
        e = r0;
        ?? r1 = new Enum("InLayoutBlock", 1);
        f = r1;
        ?? r3 = new Enum("NotUsed", 2);
        g = r3;
        h = new x40[]{r0, r1, r3};
    }

    public static x40 valueOf(String str) {
        return (x40) Enum.valueOf(x40.class, str);
    }

    public static x40[] values() {
        return (x40[]) h.clone();
    }
}
