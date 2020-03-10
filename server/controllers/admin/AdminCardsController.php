<?php

class AdminCardsController {
    // The admin cards index page
    public static function index () {
        // The pagination vars
        $page = get_page();
        $per_page = PAGINATION_LIMIT_ADMIN;

        // Check if search query is given
        if (request('q') != '') {
            $last_page = ceil(Cards::searchCount(request('q')) / $per_page);
            $cards = Cards::searchSelectPage(request('q'), $page, $per_page)->fetchAll();
        } else {
            $last_page = ceil(Cards::count() / $per_page);
            $cards = Cards::selectPage($page, $per_page)->fetchAll();
        }

        // Select the account of every card
        foreach ($cards as $card) {
            $card->account = Accounts::select($card->account_id)->fetch();
        }

        // Give all the data to the view
        return view('admin.cards.index', [
            'cards' => $cards,
            'page' => $page,
            'last_page' => $last_page
        ]);
    }

    // The admin card show page
    public static function show ($card) {
        $card->account = Accounts::select($card->account_id)->fetch();
        return view('admin.cards.show', [ 'card' => $card ]);
    }

    // The admin card delete page
    public static function delete ($card) {
        Cards::delete($card->id);
        Router::redirect('/admin/cards');
    }
}
