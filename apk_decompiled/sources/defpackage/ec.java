package defpackage;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ec {
    public static final ec e;
    public static final ec f;
    public static final /* synthetic */ ec[] g;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Enum, ec] */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Enum, ec] */
    static {
        ?? r0 = new Enum("Real", 0);
        e = r0;
        ?? r1 = new Enum("Virtual", 1);
        f = r1;
        g = new ec[]{r0, r1};
    }

    public static ec valueOf(String str) {
        return (ec) Enum.valueOf(ec.class, str);
    }

    public static ec[] values() {
        return (ec[]) g.clone();
    }
}
